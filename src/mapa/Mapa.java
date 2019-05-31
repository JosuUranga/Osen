/*
 * Copyright (c) 2000-2017 TeamDev Ltd. All rights reserved.
 * Use is subject to Apache 2.0 license terms.
 */
package mapa;


import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

import objetos.MuestraCo2;

@SuppressWarnings("serial")
public class Mapa extends MapView {
    public Mapa(MapViewOptions options,MuestraCo2 loca1,MuestraCo2 loca2,String muestra) {
        super(options);
        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                if (status == MapStatus.MAP_STATUS_OK) {
                    final Map map = getMap();
                    map.setZoom(5.0);
                    GeocoderRequest request = new GeocoderRequest();
                    request.setAddress(loca1.getLocalizacion().getNombre()+", es");

                    getServices().getGeocoder().geocode(request, new GeocoderCallback(map) {
                        @Override
                        public void onComplete(GeocoderResult[] result, GeocoderStatus status) {
                            if (status == GeocoderStatus.OK) {
                                map.setCenter(result[0].getGeometry().getLocation());
                                map.setZoom(10.0);
                                Marker marker = new Marker(map);
                                marker.setPosition(result[0].getGeometry().getLocation());

                                final InfoWindow window = new InfoWindow(map);
                                if(loca2!=null) {
                                	 window.setContent(muestra+" 1: "+loca1.getLocalizacion().getNombre()+", "+loca1.getFecha()+", "+loca1.getMetorologia());
                                }else {
                               	 	window.setContent(loca1.getLocalizacion().getNombre()+", "+loca1.getFecha()+", "+loca1.getMetorologia());
                                 
                                }
                                window.open(map, marker);
                            }
                        }
                    });
                    if(loca2!=null) {
                        GeocoderRequest request2 = new GeocoderRequest();
                        request2.setAddress(loca2.getLocalizacion().getNombre()+", es");

                        getServices().getGeocoder().geocode(request2, new GeocoderCallback(map) {
                            @Override
                            public void onComplete(GeocoderResult[] result, GeocoderStatus status) {
                                if (status == GeocoderStatus.OK) {
                                    map.setCenter(result[0].getGeometry().getLocation());
                                    map.setZoom(10.0);
                                    Marker marker = new Marker(map);
                                    marker.setPosition(result[0].getGeometry().getLocation());

                                    final InfoWindow window = new InfoWindow(map);
                                    window.setContent(muestra+" 2: "+loca2.getLocalizacion().getNombre()+", "+loca2.getFecha()+", "+loca2.getMetorologia());
                                    window.open(map, marker);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}


