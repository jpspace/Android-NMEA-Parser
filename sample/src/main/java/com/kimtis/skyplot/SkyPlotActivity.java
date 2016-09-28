package com.kimtis.skyplot;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.kimtis.MainActivity;
import com.kimtis.R;
import com.kimtis.data.CachedData;
import com.kimtis.data.SkyPlotData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uiseok.skyplotview.SkyPlotView;
import uiseok.util.TypedValueCalculate;

public class SkyPlotActivity extends AppCompatActivity {

    SkyPlotView spView;

    GraphView graph_snr;

    BarGraphSeries<DataPoint> series;

    //    Button btn_capture;
    LinearLayout linear_parent;

    TextView text_nmea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sky_plot);

        linear_parent = (LinearLayout) findViewById(R.id.linear_parent);
        graph_snr = (GraphView) findViewById(R.id.graph_snr);
        spView = (SkyPlotView) findViewById(R.id.spview);
//        spView.showDegree(false);


        // Graph SNR Settings
        final Viewport viewport = graph_snr.getViewport();
        final LegendRenderer legendRenderer = graph_snr.getLegendRenderer();
        final GridLabelRenderer labelRenderer = graph_snr.getGridLabelRenderer();

        viewport.setScrollable(true);
        viewport.setScalable(false);
        viewport.setMinX(0);
        viewport.setDrawBorder(true);
        viewport.setBorderColor(Color.BLACK);
        viewport.setXAxisBoundsManual(true);
        viewport.setXAxisBoundsStatus(Viewport.AxisBoundsStatus.FIX);
        viewport.setMaxX(35);
        graph_snr.setPadding(TypedValueCalculate.dp2pixel(10, this), TypedValueCalculate.dp2pixel(10, this), TypedValueCalculate.dp2pixel(10, this), 0);
        graph_snr.setTitle("Signal to Noise Ratio");

        labelRenderer.setHighlightZeroLines(true);
        labelRenderer.setVerticalLabelsVisible(true);
        labelRenderer.setVerticalAxisTitle(" ");

        series = new BarGraphSeries<DataPoint>();
        series.setAnimated(false);
        series.setDrawValuesOnTop(true);
        series.setDataWidth(0.5);
        series.setSpacing(20);


        graph_snr.addSeries(series);

        text_nmea = (TextView)findViewById(R.id.text_nmea);

    }

    int a = 0;

    MainActivity.OnSkyPlotAddedListener mListener = new MainActivity.OnSkyPlotAddedListener() {
        @Override
        public void onSkyPlotAdded() {

            final List<SkyPlotData> data = CachedData.getInstance().getSkyPlotData();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String gga = CachedData.getInstance().getGpgga();
                    String gsa = CachedData.getInstance().getGpgsa();
                    text_nmea.setText((gga == null ? "no gga" : gga) +
                            "\n" + (gsa == null ? "no gsa" : gsa));
                    if (a-- <= 0) {

                        // Draw SkyPlot
                        spView.removeAllSatellites();


                        for (int i = 0; i < data.size(); i++) {
                            spView.addSatellite(
                                    data.get(i).getAzElAngle().getDegreeElevation(),
                                    data.get(i).getAzElAngle().getDegreeAzimuth(),
                                    data.get(i).getPrn() + "",
                                    data.get(i).isHavePRC());

                        }
                        spView.refreshCanvas();


                        // Draw SNR Graph
                        if (CachedData.getInstance().getSatelliteDataList() != null) {
                            Map<Integer, Integer> dataPointMap = new TreeMap<Integer, Integer>();
                            for (int i = 0; i < CachedData.getInstance().getSatelliteDataList().size(); i++) {
                                if (!CachedData.getInstance().getSatelliteDataList().get(i).getPrn().equals(""))
                                    dataPointMap.put(Integer.parseInt(CachedData.getInstance().getSatelliteDataList().get(i).getPrn()),
                                            CachedData.getInstance().getSatelliteDataList().get(i).getSnr());
                            }
                            List<DataPoint> datapoints = new ArrayList<DataPoint>();
                            DataPoint[] dp = new DataPoint[datapoints.size()];
                            for (Integer key : dataPointMap.keySet()) {
                                datapoints.add(new DataPoint(key, dataPointMap.get(key)));
                            }
                            series.resetData(datapoints.toArray(dp));
                        }


                        a = 5;

                    }

                }
            });


        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        MainActivity.registerOnSkyPlotAddedListener(mListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MainActivity.unregisterOnSkyPlotAddedListener(mListener);
    }


}
