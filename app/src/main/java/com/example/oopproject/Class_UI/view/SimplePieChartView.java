package com.example.oopproject.Class_UI.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SimplePieChartView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rectF = new RectF();
    private List<PieEntry> entries = new ArrayList<>();
    private int[] colors = {0xFF00FFCC, 0xFFFF4444, 0xFF44FF44, 0xFF4444FF, 0xFFFFFF44, 0xFFFF44FF};

    public static class PieEntry {
        String label;
        float value;

        public PieEntry(String label, float value) {
            this.label = label;
            this.value = value;
        }
    }

    public SimplePieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(List<PieEntry> entries) {
        this.entries = entries;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (entries.isEmpty()) return;

        float total = 0;
        for (PieEntry entry : entries) total += entry.value;

        float width = getWidth();
        float height = getHeight();
        float radius = Math.min(width, height) / 2 * 0.8f;
        rectF.set(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius);

        float startAngle = 0;
        for (int i = 0; i < entries.size(); i++) {
            PieEntry entry = entries.get(i);
            float sweepAngle = (entry.value / total) * 360f;
            paint.setColor(colors[i % colors.length]);
            canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);
            
            // Draw simple label nearby if needed, but keeping it light
            startAngle += sweepAngle;
        }
        
        // Draw center hole for "Donut" look (cleaner)
        paint.setColor(0xFF1A1D29); // Card background color
        canvas.drawCircle(width / 2, height / 2, radius * 0.6f, paint);
        
        // Draw Title in center
        paint.setColor(0xFFFFFFFF);
        paint.setTextSize(40f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Crew", width/2, height/2 + 15, paint);
    }
}