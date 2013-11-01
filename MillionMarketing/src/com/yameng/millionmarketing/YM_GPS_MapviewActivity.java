package com.yameng.millionmarketing;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import com.baidu.mapapi.PoiOverlay;
import com.yameng.personal.Activity_PersonalCenter;
import com.yamtz.millionmarketing.R;

public class YM_GPS_MapviewActivity extends MapActivity {
	private BMapManager mapManager;
	private MKSearch mMKSearch;
	private MapView mapView;
	private MapController mapController;

	LocationListener mLocationListener = null;
	MyLocationOverlay mLocationOverlay = null;
	private Button btn_region, btn_serch;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_gps);

		btn_region = (Button) findViewById(R.id.btn_region);
		//跳转到上一页
		btn_serch = (Button) findViewById(R.id.btn_serch);
		btn_serch.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(YM_GPS_MapviewActivity.this,
						Activity_PersonalCenter.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_up_out,
						R.anim.my_alpha_action);
			}
		});

		mapManager = new BMapManager(getApplication());

		mapManager.init("F4A92D5B14A3C25A75004C2DB37191CFE798EC25", null);
		super.initMapActivity(mapManager);
		mapView = (MapView) findViewById(R.id.map_View);
		mapView.setTraffic(true);
		mapView.setBuiltInZoomControls(true);
		mapView.setDrawOverlayWhenZooming(true);

		mLocationOverlay = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(mLocationOverlay);

		mLocationListener = new LocationListener() {

			public void onLocationChanged(Location location) {
				if (location != null) {
					GeoPoint geoPoint = new GeoPoint(
							(int) (location.getLatitude() * 1e6),
							(int) (location.getLongitude() * 1e6));
					mapView.getController().animateTo(geoPoint);
					mapController = mapView.getController();
					mapController.setCenter(geoPoint);
					mapController.setZoom(16);
					MKSearch mMKSearch = new MKSearch();
					mMKSearch.init(mapManager, new MySearchListener());
					mMKSearch.poiSearchNearBy("ATM", geoPoint, 500);
				}
			}
		};

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onDestroy() {
		if (mapManager != null) {
			mapManager.destroy();
			mapManager = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (mapManager != null) {
			mapManager.getLocationManager().removeUpdates(mLocationListener);
			mLocationOverlay.disableMyLocation();
			mLocationOverlay.disableCompass();
			mapManager.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mapManager != null) {
			mapManager.getLocationManager().requestLocationUpdates(
					mLocationListener);
			mLocationOverlay.enableMyLocation();
			mLocationOverlay.enableCompass();
			mapManager.start();
		}
		super.onResume();
	}

	public class MySearchListener implements MKSearchListener {

		public void onGetAddrResult(MKAddrInfo result, int iError) {
		}

		public void onGetDrivingRouteResult(MKDrivingRouteResult result,
				int iError) {
		}

		public void onGetPoiResult(MKPoiResult result, int type, int iError) {
			if (result == null) {
				return;
			}
			PoiOverlay poioverlay = new PoiOverlay(YM_GPS_MapviewActivity.this,
					mapView);
			poioverlay.setData(result.getAllPoi());
			mapView.getOverlays().add(poioverlay);
		}

		public void onGetTransitRouteResult(MKTransitRouteResult result,
				int iError) {
		}

		public void onGetWalkingRouteResult(MKWalkingRouteResult result,
				int iError) {
		}
	}
}