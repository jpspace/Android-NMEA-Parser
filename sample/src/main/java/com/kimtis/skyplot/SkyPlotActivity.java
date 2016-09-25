package com.kimtis.skyplot;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

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

import java.util.List;

import uiseok.skyplotview.SkyPlotView;
import uiseok.util.TypedValueCalculate;

public class SkyPlotActivity extends AppCompatActivity {

    SkyPlotView spView;

    GraphView graph_snr;

    BarGraphSeries<DataPoint> series;

//    Button btn_capture;
    LinearLayout linear_parent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sky_plot);

        linear_parent = (LinearLayout)findViewById(R.id.linear_parent);
        graph_snr = (GraphView)findViewById(R.id.graph_snr);
        spView = (SkyPlotView)findViewById(R.id.spview);
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
        viewport.setMaxX(30);
        graph_snr.setPadding(TypedValueCalculate.dp2pixel(10,this),TypedValueCalculate.dp2pixel(10,this),TypedValueCalculate.dp2pixel(10,this),0);
        graph_snr.setTitle("Signal to Noise Ratio");

        labelRenderer.setHighlightZeroLines(true);
        labelRenderer.setVerticalLabelsVisible(true);
        labelRenderer.setVerticalAxisTitle(" ");

        series = new BarGraphSeries<DataPoint>();
        series.setAnimated(false);
        series.setDrawValuesOnTop(true);
        series.setDataWidth(0.5);
        series.setSpacing(20);

        series.appendData(new DataPoint(1, 20), false, 50);
        series.appendData(new DataPoint(2, 30), false, 50);
        series.appendData(new DataPoint(3, 15), false, 50);
        series.appendData(new DataPoint(4, 40), false, 50);
        series.appendData(new DataPoint(5, 25), false, 50);

        graph_snr.addSeries(series);



    }

    MainActivity.OnNmeaCPCalculatedListener mListener = new MainActivity.OnNmeaCPCalculatedListener() {
        @Override
        public void onNmeaCPCalculated() {
            // TODO
            Log.e("Listener Activated", "activated");
            List<SkyPlotData> data = CachedData.getInstance().getSkyPlotData();
            for(int i=0; i< data.size(); i++) {
                spView.drawSatellite(data.get(i).getAzElAngle().getDegreeElevation(), data.get(i).getAzElAngle().getDegreeAzimuth(),data.get(i).getPrn()+"");
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        MainActivity.registerOnNmeaCPCalculatedListener(mListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MainActivity.unregisterOnNmeaCPCalculatedListener(mListener);
    }
}
