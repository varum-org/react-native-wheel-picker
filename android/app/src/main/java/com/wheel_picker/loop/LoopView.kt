package com.wheel_picker.loop

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Handler
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.math.sin

class LoopView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val r = Rect()

    var simpleOnGestureListener: SimpleOnGestureListener = LoopViewGestureListener(this)
    var gestureDetector: GestureDetector =  GestureDetector(context, simpleOnGestureListener)
    var handlerMessage: Handler = MessageHandler(this)
    var paintA = Paint()
    var paintB = Paint()
    var paintC = Paint()

    var future: ScheduledFuture<*>? = null
    var selectedItem = 0
    var executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    var arrayList: ArrayList<*> = arrayListOf<Any>()
        set(value) {
            field = value
            initData()
            invalidate()
        }
    var loopListener: LoopListener? = null
        set(value) {
            if (value != null) {
                field = value
            }
        }
    private var _measuredHeight = 0
    var totalScrollY = 0
    var textSize = 0
        set(value) {
            if (value > 0.0f) {
                field = (context.resources.displayMetrics.density * value).toInt()
            }
        }
    private var maxTextWidth = 0
    var maxTextHeight = 0
    private var colorGrayLight = -0x3a3a3b
    var lineSpacingMultiplier = 2.0f
    var isLoop = false
    var firstLineY = 0
    var secondLineY = 0
    var preCurrentIndex = 0
    var initPosition = 0
    var itemCount = 7
    var halfCircumference = 0
    var radius = 0
    var change = 0
    var y1 = 0.0f
    var y2 = 0.0f
    var dy = 0.0f

    init {
        textSize = 16
        paintA.color = colorGrayLight
        paintB.textSize = textSize.toFloat()
        paintA.textSize = textSize.toFloat()
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        gestureDetector.setIsLongpressEnabled(false)
    }

    private fun initData() {
        if (arrayList.isNullOrEmpty()) {
            return
        }
        paintA.isAntiAlias = true
        paintB.isAntiAlias = true
        paintC.apply {
            isAntiAlias = true
            typeface = Typeface.MONOSPACE
            textSize = textSize.toFloat()
        }
        measureTextWidthHeight()
        halfCircumference = (maxTextHeight * lineSpacingMultiplier * (itemCount - 1)).toInt()
        _measuredHeight = (halfCircumference * 2 / Math.PI).toInt()
        radius = (halfCircumference / Math.PI).toInt()
        firstLineY = ((_measuredHeight - lineSpacingMultiplier * maxTextHeight) / 2.0f).toInt()
        secondLineY = ((_measuredHeight + lineSpacingMultiplier * maxTextHeight) / 2.0f).toInt()
        if (initPosition == -1) {
            initPosition = if (isLoop) {
                (arrayList.size + 1) / 2
            } else {
                0
            }
        }
        preCurrentIndex = initPosition
    }

    private fun measureTextWidthHeight() {
        val rect = Rect()
        for (i in arrayList.indices) {
            val s1 = arrayList[i] as String
            paintB.getTextBounds(s1, 0, s1.length, rect)
            val textWidth = rect.width()
            if (textWidth > maxTextWidth) {
                maxTextWidth = textWidth
            }
            paintB.getTextBounds("\u661F\u671F", 0, 2, rect) // 星期
            val textHeight = rect.height()
            if (textHeight > maxTextHeight) {
                maxTextHeight = textHeight
            }
        }
    }

    private fun smoothScroll() {
        val offset = (totalScrollY % (lineSpacingMultiplier * maxTextHeight)).toInt()
        cancelFuture()
        future = executor.scheduleWithFixedDelay(
            MTimer(this, offset),
            0,
            10,
            TimeUnit.MILLISECONDS
        )
    }

    private fun drawCenter(
        canvas: Canvas,
        paint: Paint,
        text: String,
        y: Int
    ) {
        canvas.getClipBounds(r)
        val cWidth = r.width()
        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds(text, 0, text.length, r)
        val x = cWidth / 2f - r.width() / 2f - r.left
        canvas.drawText(text, x, y.toFloat(), paint)
    }

    fun smoothScroll(velocityY: Float) {
        cancelFuture()
        val velocityFling = 20
        future = executor.scheduleWithFixedDelay(
            LoopTimerTask(this, velocityY),
            0,
            velocityFling.toLong(),
            TimeUnit.MILLISECONDS
        )
    }

    fun itemSelected() {
        loopListener?.let {
            postDelayed(LoopRunnable(this), 200L)
        }
    }

    fun cancelFuture() {
        future?.let {
            if (!it.isCancelled) {
                it.cancel(true)
                future = null
            }
        }
    }


    override fun onDraw(canvas: Canvas) {
        if (arrayList.isNullOrEmpty()) {
            super.onDraw(canvas)
            return
        }
        val arrString: Array<String?> = arrayOfNulls(itemCount)
        change = (totalScrollY / (lineSpacingMultiplier * maxTextHeight)).toInt()
        preCurrentIndex = initPosition + change % arrayList.size
        if (!isLoop) {
            if (preCurrentIndex < 0) {
                preCurrentIndex = 0
            }
            if (preCurrentIndex > arrayList.size - 1) {
                preCurrentIndex = arrayList.size - 1
            }
        } else {
            if (preCurrentIndex < 0) {
                preCurrentIndex += arrayList.size
            }
            if (preCurrentIndex > arrayList.size - 1) {
                preCurrentIndex -= arrayList.size
            }
        }
        val j2 = (totalScrollY % (lineSpacingMultiplier * maxTextHeight)).toInt()
        var k1 = 0
        while (k1 < itemCount) {
            var l1 = preCurrentIndex - (itemCount / 2 - k1)
            if (isLoop) {
                if (l1 < 0) {
                    l1 += arrayList.size
                }
                if (l1 > arrayList.size - 1) {
                    l1 -= arrayList.size
                }
                arrString[k1] = arrayList[l1].toString()
            } else if (l1 < 0) {
                arrString[k1] = ""
            } else if (l1 > arrayList.size - 1) {
                arrString[k1] = ""
            } else {
                arrString[k1] = arrayList[l1].toString()
            }
            k1++
        }
        canvas.drawLine(
            0.0f,
            firstLineY.toFloat(),
            measuredWidth.toFloat(),
            firstLineY.toFloat(),
            paintC
        )
        canvas.drawLine(
            0.0f,
            secondLineY.toFloat(),
            measuredWidth.toFloat(),
            secondLineY.toFloat(),
            paintC
        )
        var j1 = 0
        while (j1 < itemCount) {
            canvas.save()
            val itemHeight = maxTextHeight * lineSpacingMultiplier
            val radian =
                (itemHeight * j1 - j2) * Math.PI / halfCircumference
            val angle = (90.0 - radian / Math.PI * 180.0).toFloat()
            if (angle >= 90f || angle <= -90f) {
                canvas.restore()
            } else {
                val translateY =
                    (radius - Math.cos(radian) * radius - sin(radian) * maxTextHeight / 2.0).toInt()
                canvas.translate(0.0f, translateY.toFloat())
                canvas.scale(1.0f, sin(radian).toFloat())
                if (translateY <= firstLineY && maxTextHeight + translateY >= firstLineY) {
                    canvas.save()
                    canvas.clipRect(0, 0, measuredWidth, firstLineY - translateY)
                    drawCenter(canvas, paintA, arrString[j1]!!, maxTextHeight)
                    canvas.restore()
                    canvas.save()
                    canvas.clipRect(0, firstLineY - translateY, measuredWidth, itemHeight.toInt())
                    drawCenter(canvas, paintB, arrString[j1]!!, maxTextHeight)
                    canvas.restore()
                } else if (translateY <= secondLineY && maxTextHeight + translateY >= secondLineY) {
                    canvas.save()
                    canvas.clipRect(0, 0, measuredWidth, secondLineY - translateY)
                    drawCenter(canvas, paintB, arrString[j1]!!, maxTextHeight)
                    canvas.restore()
                    canvas.save()
                    canvas.clipRect(
                        0, secondLineY - translateY, measuredWidth,
                        itemHeight.toInt()
                    )
                    drawCenter(canvas, paintA, arrString[j1]!!, maxTextHeight)
                    canvas.restore()
                } else if (translateY >= firstLineY && maxTextHeight + translateY <= secondLineY) {
                    canvas.clipRect(0, 0, measuredWidth, itemHeight.toInt())
                    drawCenter(canvas, paintB, arrString[j1]!!, maxTextHeight)
                    selectedItem = arrayList.indexOf(arrString[j1])
                } else {
                    canvas.clipRect(0, 0, measuredWidth, itemHeight.toInt())
                    drawCenter(canvas, paintA, arrString[j1]!!, maxTextHeight)
                }
                canvas.restore()
            }
            j1++
        }
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        initData()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(motionevent: MotionEvent): Boolean {
        when (motionevent.action) {
            MotionEvent.ACTION_DOWN -> {
                y1 = motionevent.rawY
                parent?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                y2 = motionevent.rawY
                dy = y1 - y2
                y1 = y2
                totalScrollY = (totalScrollY.toFloat() + dy).toInt()
                if (!isLoop) {
                    val initPositionCircleLength =
                        (initPosition * (lineSpacingMultiplier * maxTextHeight)).toInt()
                    val initPositionStartY = -1 * initPositionCircleLength
                    if (totalScrollY < initPositionStartY) {
                        totalScrollY = initPositionStartY
                    }
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (!gestureDetector.onTouchEvent(motionevent) && motionevent.action == MotionEvent.ACTION_UP) {
                    smoothScroll()
                }
                parent?.requestDisallowInterceptTouchEvent(false)
                return true
            }
            else -> {
                if (!gestureDetector.onTouchEvent(motionevent) && motionevent.action == MotionEvent.ACTION_UP) {
                    smoothScroll()
                }
                parent?.requestDisallowInterceptTouchEvent(false)
                return true
            }
        }
        if (!isLoop) {
            val circleLength =
                ((arrayList.size - 1 - initPosition).toFloat() * (lineSpacingMultiplier * maxTextHeight)).toInt()
            if (totalScrollY >= circleLength) {
                totalScrollY = circleLength
            }
        }
        invalidate()
        if (!gestureDetector.onTouchEvent(motionevent) && motionevent.action == MotionEvent.ACTION_UP) {
            smoothScroll()
        }
        return true
    }

    fun setSelectedItemTextColor(color: Int) {
        paintB.color = color
    }

    fun setSelectedItemTextSize(textSize: Int) {
        val scaledSizeInPixels =
            textSize * resources.displayMetrics.scaledDensity
        paintB.textSize = scaledSizeInPixels
    }

    fun setSelectedItemFont(font: Typeface?) {
        paintB.typeface = font
    }

    fun setItemTextColor(color: Int) {
        paintA.color = color
    }

    fun setItemTextSize(textSize: Int) {
        val scaledSizeInPixels =
            textSize * resources.displayMetrics.scaledDensity
        paintA.textSize = scaledSizeInPixels
    }

    fun setItemFont(font: Typeface?) {
        paintA.typeface = font
    }

    fun setIndicatorColor(color: Int) {
        paintC.color = color
    }

    fun setIndicatorWidth(width: Int) {
        paintC.strokeWidth = width.toFloat()
    }

    fun hideIndicator() {
        paintC.color = Color.TRANSPARENT
    }

    fun setPositionSelectedItem(position: Int) {
        totalScrollY =
            ((position - initPosition).toFloat() * (lineSpacingMultiplier * maxTextHeight)).toInt()
        invalidate()
        smoothScroll()
    }

    companion object {
        fun getSelectedItem(loopView: LoopView): Int {
            return loopView.selectedItem
        }

        fun smoothScroll(loopView: LoopView) {
            loopView.smoothScroll()
        }
    }
}
