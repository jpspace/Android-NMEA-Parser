package com.kimtis.graph;

import android.graphics.Color;
import android.graphics.Paint;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.kimtis.MainActivity;
import com.kimtis.MyApplication;
import com.kimtis.R;
import com.kimtis.data.CachedData;
import com.kimtis.data.EstmData;
import com.ppsoln.commons.position.NEV;

import java.util.LinkedList;
import java.util.Random;

import uiseok.skyplotview.SkyPlotViewCustom;
import uiseok.util.TypedValueCalculate;

public class GraphActivity extends AppCompatActivity {

    GraphView graphN, graphE, graphV;

    LocationManager mLM;


    TextView btn_stop, btn_start, btn_change;

    LineGraphSeries<DataPoint>
            n_series_before, n_series_modified,
            e_series_before, e_series_modified,
            v_series_before, v_series_modified;

    LinearLayout linear_nev, linear_plot;
    TextView text_r_h_err, text_r_v_err, text_r_3d_err;
    TextView text_m_h_err, text_m_v_err, text_m_3d_err;

    SkyPlotViewCustom errorView;

    int i = 0;
    double mLastRandom = 2;
    Random mRand = new Random();
    long x_zero_time = 0L;

    double avg_raw_horizontal_error = 0;
    double avg_raw_vertical_error = 0;
    double avg_raw_3d_error = 0;
    double avg_modified_horizontal_error = 0;
    double avg_modified_vertical_error = 0;
    double avg_modified_3d_error = 0;


    boolean isFirst = true;
//    private double getRandom() {
//        return mLastRandom += mRand.nextDouble() - mRand.nextDouble();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        linear_nev = (LinearLayout) findViewById(R.id.linear_nev);
        linear_plot = (LinearLayout) findViewById(R.id.linear_plot);
        text_r_h_err = (TextView) findViewById(R.id.text_r_hor_error);
        text_r_v_err = (TextView) findViewById(R.id.text_r_ver_error);
        text_r_3d_err = (TextView) findViewById(R.id.text_r_3d_error);
        text_m_h_err = (TextView) findViewById(R.id.text_m_hor_error);
        text_m_v_err = (TextView) findViewById(R.id.text_m_ver_error);
        text_m_3d_err = (TextView) findViewById(R.id.text_m_3d_error);


        graphN = (GraphView) findViewById(R.id.graph_n);
        graphE = (GraphView) findViewById(R.id.graph_e);
        graphV = (GraphView) findViewById(R.id.graph_v);

        // N_Graph Settings
        final Viewport n_viewport = graphN.getViewport();
        final LegendRenderer nlegendRenderer = graphN.getLegendRenderer();
        final GridLabelRenderer nlabelRenderer = graphN.getGridLabelRenderer();

        n_viewport.setScrollable(true);
        n_viewport.setScalable(true);
        n_viewport.setMinX(0);
        n_viewport.setDrawBorder(true);
        n_viewport.setBorderColor(Color.BLACK);
        graphN.setPadding(TypedValueCalculate.dp2pixel(10, this), TypedValueCalculate.dp2pixel(10, this), TypedValueCalculate.dp2pixel(10, this), 0);
        graphN.setTitle("Error with N");

        nlegendRenderer.setVisible(true);
        nlegendRenderer.setBackgroundColor(Color.WHITE);
        nlegendRenderer.setTextSize(TypedValueCalculate.dp2pixel(9, this));
        nlegendRenderer.setAlign(LegendRenderer.LegendAlign.TOP);
        graphN.setLegendRenderer(nlegendRenderer);

        nlabelRenderer.setHighlightZeroLines(true);
        nlabelRenderer.setVerticalLabelsVisible(true);
        nlabelRenderer.setVerticalAxisTitle(" ");


        n_series_before = new LineGraphSeries<DataPoint>();
        n_series_before.setAnimated(true);
        n_series_before.setDrawDataPoints(false);
        n_series_before.setTitle("Raw");

        graphN.addSeries(n_series_before);

        Paint modified_paint = new Paint();
        modified_paint.setColor(Color.RED);
        modified_paint.setStrokeWidth(TypedValueCalculate.dp2pixel(1, MyApplication.getContext()));

        n_series_modified = new LineGraphSeries<DataPoint>();
        n_series_modified.setAnimated(true);
        n_series_modified.setDrawDataPoints(false);
        n_series_modified.setCustomPaint(modified_paint);
        n_series_modified.setTitle("Modified");
        n_series_modified.setColor(Color.RED);
        graphN.addSeries(n_series_modified);

        // E_Graph Settings
        final Viewport e_viewport = graphE.getViewport();
        final LegendRenderer elegendRenderer = graphE.getLegendRenderer();
        final GridLabelRenderer elabelRenderer = graphE.getGridLabelRenderer();

        e_viewport.setScrollable(true);
        e_viewport.setScalable(true);
        e_viewport.setMinX(0);
        e_viewport.setDrawBorder(true);
        e_viewport.setBorderColor(Color.BLACK);
        graphE.setPadding(TypedValueCalculate.dp2pixel(10, this), TypedValueCalculate.dp2pixel(10, this), TypedValueCalculate.dp2pixel(10, this), 0);
        graphE.setTitle("Error with E");
        elabelRenderer.setHighlightZeroLines(true);
        elabelRenderer.setHighlightZeroLines(true);
        elabelRenderer.setVerticalLabelsVisible(true);
        elabelRenderer.setVerticalAxisTitle(" ");

        elegendRenderer.setVisible(true);
        elegendRenderer.setBackgroundColor(Color.WHITE);
        elegendRenderer.setTextSize(TypedValueCalculate.dp2pixel(9, this));
        elegendRenderer.setAlign(LegendRenderer.LegendAlign.TOP);
        graphE.setLegendRenderer(elegendRenderer);

        e_series_before = new LineGraphSeries<DataPoint>();
        e_series_before.setAnimated(true);
        e_series_before.setDrawDataPoints(false);
        e_series_before.setTitle("Raw");
        graphE.addSeries(e_series_before);

        e_series_modified = new LineGraphSeries<DataPoint>();
        e_series_modified.setAnimated(true);
        e_series_modified.setDrawDataPoints(false);
        e_series_modified.setCustomPaint(modified_paint);
        e_series_modified.setTitle("Modified");
        e_series_modified.setColor(Color.RED);
        graphE.addSeries(e_series_modified);

        // V_Graph Settings
        final Viewport v_viewport = graphV.getViewport();
        final LegendRenderer vlegendRenderer = graphV.getLegendRenderer();
        final GridLabelRenderer vlabelRenderer = graphV.getGridLabelRenderer();

        v_viewport.setScrollable(true);
        v_viewport.setScalable(true);
        v_viewport.setMinX(0);
        v_viewport.setDrawBorder(true);
        v_viewport.setBorderColor(Color.BLACK);
        graphV.setPadding(TypedValueCalculate.dp2pixel(10, this), TypedValueCalculate.dp2pixel(10, this), TypedValueCalculate.dp2pixel(10, this), 0);
        graphV.setTitle("Error with V");
        vlabelRenderer.setHighlightZeroLines(true);
        vlabelRenderer.setHighlightZeroLines(true);
        vlabelRenderer.setVerticalLabelsVisible(true);
        vlabelRenderer.setVerticalAxisTitle(" ");

        vlegendRenderer.setVisible(true);
        vlegendRenderer.setBackgroundColor(Color.WHITE);
        vlegendRenderer.setTextSize(TypedValueCalculate.dp2pixel(9, this));
        vlegendRenderer.setAlign(LegendRenderer.LegendAlign.TOP);
        graphV.setLegendRenderer(vlegendRenderer);

        v_series_before = new LineGraphSeries<DataPoint>();
        v_series_before.setAnimated(true);
        v_series_before.setDrawDataPoints(false);
        v_series_before.setTitle("Raw");
        graphV.addSeries(v_series_before);

        v_series_modified = new LineGraphSeries<DataPoint>();
        v_series_modified.setAnimated(true);
        v_series_modified.setDrawDataPoints(false);
        v_series_modified.setCustomPaint(modified_paint);
        v_series_modified.setTitle("Modified");
        v_series_modified.setColor(Color.RED);
        graphV.addSeries(v_series_modified);

        errorView = (SkyPlotViewCustom) findViewById(R.id.error_view);

        btn_start = (TextView) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.registerOnSkyPlotAddedListener(nListener);
                MainActivity.registerOnNmeaCPCalculatedListener(mListener);
            }
        });
        btn_stop = (TextView) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.unregisterOnSkyPlotAddedListener(nListener);
                MainActivity.unregisterOnNmeaCPCalculatedListener(mListener);
            }
        });

        btn_change = (TextView) findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linear_nev.getVisibility() == LinearLayout.VISIBLE) {
                    linear_nev.setVisibility(LinearLayout.GONE);
                    linear_plot.setVisibility(LinearLayout.VISIBLE);
                } else {
                    linear_nev.setVisibility(LinearLayout.VISIBLE);
                    linear_plot.setVisibility(LinearLayout.GONE);
                }
            }
        });


    }

    MainActivity.OnSkyPlotAddedListener nListener = new MainActivity.OnSkyPlotAddedListener() {
        @Override
        public void onSkyPlotAdded() {





        }

    };

    MainActivity.OnNmeaCPCalculatedListener mListener = new MainActivity.OnNmeaCPCalculatedListener() {
        @Override
        public void onNmeaCPCalculated() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (!((LinkedList) CachedData.getInstance().getEstmDataList()).isEmpty() && isFirst) {
                        x_zero_time = ((EstmData) ((LinkedList) CachedData.getInstance().getEstmDataList()).getFirst()).getTime().getTime() / 1000;
                        isFirst = false;
                    }

                    if (x_zero_time != 0L) {
                        EstmData current_estm = (EstmData) ((LinkedList) CachedData.getInstance().getEstmDataList()).getLast();
                        NEV before_nev = current_estm.getNEV(current_estm.getBefore_latLngAlt());
                        NEV modified_nev = current_estm.getNEV(current_estm.getModified_latLngAlt());


                        n_series_modified.appendData(new DataPoint(
                                (double) (current_estm.getTime().getTime() / 1000 - x_zero_time),
                                modified_nev.getN()), true, 100);


                        e_series_modified.appendData(new DataPoint(
                                (double) (current_estm.getTime().getTime() / 1000 - x_zero_time),
                                modified_nev.getE()), true, 100);


                        v_series_modified.appendData(new DataPoint(
                                (double) (current_estm.getTime().getTime() / 1000 - x_zero_time),
                                modified_nev.getV()), true, 100);


                        // TODO
                        errorView.drawErrorPoint(
                                modified_nev.getN(),
                                modified_nev.getE(),
                                modified_nev.getV(),
                                SkyPlotViewCustom.TYPE_MODIFIED
                        );
                        errorView.drawErrorPoint(-3.0,-5.0,0.0,SkyPlotViewCustom.TYPE_MODIFIED);


                        avg_modified_horizontal_error = avg_modified_horizontal_error * (CachedData.getInstance().getEstmDataListSize() - 1) /
                                CachedData.getInstance().getEstmDataListSize() +
                                Math.sqrt(Math.pow(modified_nev.getE(), 2) +
                                        Math.pow(modified_nev.getN(), 2)) * 1 / CachedData.getInstance().getEstmDataListSize();

                        avg_modified_vertical_error = avg_modified_vertical_error * (CachedData.getInstance().getEstmDataListSize() - 1) /
                                CachedData.getInstance().getEstmDataListSize() +
                                Math.abs(modified_nev.getV()) * 1 / CachedData.getInstance().getEstmDataListSize();

                        avg_modified_3d_error = avg_modified_3d_error * (CachedData.getInstance().getEstmDataListSize() - 1) /
                                CachedData.getInstance().getEstmDataListSize() +
                                Math.sqrt(Math.pow(modified_nev.getE(), 2) +
                                        Math.pow(modified_nev.getN(), 2) +
                                        Math.pow(modified_nev.getV(), 2)) * 1 / CachedData.getInstance().getEstmDataListSize();


                        text_m_h_err.setText(String.format("M Horizontal Error\n%.2f", avg_modified_horizontal_error));
                        text_m_v_err.setText(String.format("M Vertical Error\n%.2f", avg_modified_vertical_error));
                        text_m_3d_err.setText(String.format("M 3D Error\n%.2f", avg_modified_3d_error));


                        errorView.drawErrorPoint(
                                before_nev.getN(),
                                before_nev.getE(),
                                before_nev.getV(),
                                SkyPlotViewCustom.TYPE_BEFORE);
                        n_series_before.appendData(
                                new DataPoint(
                                        (double) (current_estm.getTime().getTime() / 1000 - x_zero_time),
                                        before_nev.getN()),
                                true,
                                100);
                        e_series_before.appendData(
                                new DataPoint(
                                        (double) (current_estm.getTime().getTime() / 1000 - x_zero_time),
                                        before_nev.getE()),
                                true,
                                100);

                        v_series_before.appendData(
                                new DataPoint(
                                        (double) (current_estm.getTime().getTime() / 1000 - x_zero_time),
                                        before_nev.getV()),
                                true,
                                100);
                        avg_raw_horizontal_error = avg_raw_horizontal_error * (CachedData.getInstance().getEstmDataListSize() - 1) /
                                CachedData.getInstance().getEstmDataListSize() +
                                Math.sqrt(Math.pow(before_nev.getE(), 2) +
                                        Math.pow(before_nev.getN(), 2)) * 1 / CachedData.getInstance().getEstmDataListSize();

                        avg_raw_vertical_error = avg_raw_vertical_error * (CachedData.getInstance().getEstmDataListSize() - 1) /
                                CachedData.getInstance().getEstmDataListSize() +
                                Math.abs(before_nev.getV()) * 1 / CachedData.getInstance().getEstmDataListSize();

                        avg_raw_3d_error = avg_raw_3d_error * (CachedData.getInstance().getEstmDataListSize() - 1) /
                                CachedData.getInstance().getEstmDataListSize() +
                                Math.sqrt(Math.pow(before_nev.getE(), 2) +
                                        Math.pow(before_nev.getN(), 2) +
                                        Math.pow(before_nev.getV(), 2)) * 1 / CachedData.getInstance().getEstmDataListSize();

                        text_r_h_err.setText(String.format("R Horizontal Error\n%.2f", avg_raw_horizontal_error));
                        text_r_v_err.setText(String.format("R Vertical Error\n%.2f", avg_raw_vertical_error));
                        text_r_3d_err.setText(String.format("R 3D Error\n%.2f", avg_raw_3d_error));


                    }
                }
            });


        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        MainActivity.registerOnNmeaCPCalculatedListener(mListener);
        MainActivity.registerOnSkyPlotAddedListener(nListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MainActivity.unregisterOnSkyPlotAddedListener(nListener);
        MainActivity.unregisterOnNmeaCPCalculatedListener(mListener);
    }

}
