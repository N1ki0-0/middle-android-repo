package com.example.androidpracticumcustomview.ui.theme

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */

class CustomContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var maxWidth = 0
        var totalHeight = 0

        for(i in 0 until childCount){
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            maxWidth = maxOf(maxWidth, child.measuredWidth)
            totalHeight += child.measuredHeight
        }

        val width = resolveSize(maxWidth, widthMeasureSpec)
        val height = resolveSize(totalHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val containerWidth = right - left
        val containerHeight = bottom - top

        for(i in 0 until childCount){
            val child = getChildAt(i)

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            val left = (containerWidth - childWidth) / 2

            if(i == 0){
                child.layout(left, 0, left + childWidth, childHeight)
            }else if(i == 1){
                child.layout(left, containerHeight - childHeight, left + childWidth, containerHeight)
            }
        }
    }

    override fun addView(child: View) {
        if(childCount > 2){
            throw IllegalStateException("не более двух элементов")
        }

        child?.alpha = 0f
        child?.translationY = height / 2f

        super.addView(child)

        val containerHeight = height
        val direction = if (childCount == 1) -1 else 1 // Первый — вверх, второй — вниз
        val startTranslationY = containerHeight / 2f * direction

        child.translationY = startTranslationY

        child.animate()
            .alpha(1f)
            .setDuration(2000)
            .start()

        child.animate()
            .translationY(0f)
            .setDuration(5000)
            .start()
    }
}