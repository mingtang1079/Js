package com.example.administrator.js.activity.locaiton;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.appbaselib.base.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ChooseLocationActivity extends BaseActivity implements AMapLocationListener, LocationSource, PoiSearch.OnPoiSearchListener, AMap.OnCameraChangeListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_search)
    TextView mEtSearch;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.iv_center_location)
    ImageView mImageViewCenter;

    MyLocationStyle myLocationStyle;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private double mCurrentLat;
    private double mCurrentLng;
    private float zoom = 14;//地图缩放级别

    ArrayList<PoiItem> poiItemsList = new ArrayList<>();
    private PoiItem userSelectPoiItem;

    AddressAdapter mAddressAdapter;

    private ObjectAnimator mTransAnimator;//地图中心标志动态

    MenuItem mMenuItem;

    protected void initMenu() {
        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setTitle("确定");
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {


                Intent intent = new Intent();
                intent.putExtra("data", userSelectPoiItem);
                setResult(Activity.RESULT_OK, intent);
                finish();

                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        //初始化地图控制器对象

        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        //设置定位监听
        aMap.setLocationSource(this);
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(false);
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(false);
        aMap.setOnCameraChangeListener(this); // 添加移动地图事件监听器
        settings.setZoomControlsEnabled(false);//缩放按钮
        initMap();
    }

    private void initMap() {
        mlocationClient = new AMapLocationClient(this);
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位监听
        mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        mlocationClient.startLocation();
    }

    /**
     * 搜索poi
     *
     * @param key      关键字
     * @param pageNum  页码
     * @param cityCode 城市代码，或者城市名称
     * @param nearby   是否搜索周边
     */
    void searchPoi(String key, int pageNum, String cityCode, boolean nearby) {

        query = new PoiSearch.Query(key, "", cityCode);
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，
        //POI搜索类型共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(50);// 设置每页最多返回多少条poiitem
        query.setPageNum(pageNum);//设置查询页码
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        if (nearby)
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(mCurrentLat,
                    mCurrentLng), 1500));//设置周边搜索的中心点以及半径
        poiSearch.searchPOIAsyn();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_choose_location;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("选择位置");
        initMenu();
        mAddressAdapter = new AddressAdapter(R.layout.item_address_map, poiItemsList);
        mAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAddressAdapter.setSingleChoosed(position);
                userSelectPoiItem = mAddressAdapter.getData().get(position);
                //设置图钉选项
//                MarkerOptions markerOptions = new MarkerOptions();
////                options.icon(BitmapDescriptorFactory.defaultMarker());//默认图标
//                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_address)); //图标
//                markerOptions.position(new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude())); //位置
//                markerOptions.title(poiItem.getProvinceName() + "" + poiItem.getCityName() + "" + poiItem.getAdName()); //标题
//                markerOptions.snippet(poiItem.getSnippet()); //子标题
//                markerOptions.period(60); //设置多少帧刷新一次图片资源
//                aMap.addMarker(markerOptions);

                aMap.clear();
                CameraUpdate cu = CameraUpdateFactory.newLatLng(new LatLng(userSelectPoiItem.getLatLonPoint().getLatitude(), userSelectPoiItem.getLatLonPoint().getLongitude()));
                aMap.animateCamera(cu);
                aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
            }
        });
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mRecyclerview.setAdapter(mAddressAdapter);
        mTransAnimator = ObjectAnimator.ofFloat(mImageViewCenter, "translationY", 0f, -80f, 0f);
        mTransAnimator.setDuration(800);

        //逆地址搜索监听器
        mOnGeocodeSearchListener = new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                if (i == 1000) {
                    mAddressAdapter.setNewData(regeocodeResult.getRegeocodeAddress().getPois());
                }

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        };
    }

    /**
     * 移动动画
     */
    private void startTransAnimator() {
        if (null != mTransAnimator && !mTransAnimator.isRunning()) {
            mTransAnimator.start();
        }
    }

    @OnClick(R.id.et_search)
    public void onViewClicked() {

        // TODO: 2018/6/27 搜索

        startForResult(LocationSearchActivity.class, 20);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //存储定位数据
                mCurrentLat = amapLocation.getLatitude();
                mCurrentLng = amapLocation.getLongitude();
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                doWhenLocationSucess(amapLocation);
            }
        } else {
            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            Log.e("AmapError", "location Error, ErrCode:"
                    + amapLocation.getErrorCode() + ", errInfo:"
                    + amapLocation.getErrorInfo());
        }
    }

    /**
     * 当定位成功需要做的事情
     */
    private void doWhenLocationSucess(AMapLocation location) {
        userSelectPoiItem = GeoCoderUtil.changeToPoiItem(location);
       // searchPoi("", 1, userSelectPoiItem.getCityCode(), true);
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userSelectPoiItem.getLatLonPoint().getLatitude(), userSelectPoiItem.getLatLonPoint().getLongitude()), zoom));
    }

    //自定义一个图钉，并且设置图标，当我们点击图钉时，显示设置的信息
    private MarkerOptions getMarkerOptions(AMapLocation amapLocation) {
        MarkerOptions markerOptions = new MarkerOptions(); //设置图钉选项
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_address)); //图标
        markerOptions.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())); //位置
//        new StringBuffer().append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
        markerOptions.title(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getDistrict()); //标题
        markerOptions.snippet(amapLocation.getStreet() + "" + amapLocation.getStreetNum()); //子标题
        markerOptions.period(60);  //设置多少帧刷新一次图片资源
        return markerOptions;

    }

    @Override
    public void activate(OnLocationChangedListener mOnLocationChangedListener) {
        mListener = mOnLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    public void onPoiSearched(PoiResult mPoiResult, int mI) {
        poiItemsList = mPoiResult.getPois();
        mAddressAdapter.setNewData(poiItemsList);
    }

    @Override
    public void onPoiItemSearched(com.amap.api.services.core.PoiItem mPoiItem, int mI) {
        Log.e("bbb+++++", mPoiItem.getAdName() + mPoiItem.getCityName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == Activity.RESULT_OK) {
            userSelectPoiItem = data.getParcelableExtra("PoiItem");
            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userSelectPoiItem.getLatLonPoint().getLatitude(), userSelectPoiItem.getLatLonPoint().getLongitude()), zoom));
            searchPoi("", 0, userSelectPoiItem.getCityCode(), true);
        }
    }

    @Override
    public void onCameraChange(CameraPosition mCameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(final CameraPosition cameraPosition) {
        LatLngEntity latLngEntity = new LatLngEntity(cameraPosition.target.latitude, cameraPosition.target.longitude);
        getAddressInfoByLatLong(cameraPosition.target.latitude, cameraPosition.target.longitude);

        startTransAnimator();

    }

    private GeocodeSearch.OnGeocodeSearchListener mOnGeocodeSearchListener;

    /**
     * 通过经纬度获取当前地址详细信息，逆地址编码
     *
     * @param latitude
     * @param longitude
     */
    private void getAddressInfoByLatLong(double latitude, double longitude) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        /*
        point - 要进行逆地理编码的地理坐标点。
        radius - 查找范围。默认值为1000，取值范围1-3000，单位米。
        latLonType - 输入参数坐标类型。包含GPS坐标和高德坐标。 可以参考RegeocodeQuery.setLatLonType(String)
        */
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latitude, longitude), 3000, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
        geocodeSearch.setOnGeocodeSearchListener(mOnGeocodeSearchListener);
    }

}
