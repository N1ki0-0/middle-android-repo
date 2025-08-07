package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */
@Composable
fun CustomContainerCompose(
    firstChild: @Composable (() -> Unit)?,
    secondChild: @Composable (() -> Unit)?
) {
    val scope = rememberCoroutineScope()

    val firstAlpha = remember { Animatable(0f) }
    val firstOffset = remember { Animatable(0f) }

    val secondAlpha = remember { Animatable(0f) }
    val secondOffset = remember { Animatable(0f) }

    // Блок активации анимации при первом запуске
    LaunchedEffect(Unit) {
        firstOffset.snapTo(0f)
        firstAlpha.animateTo(1f)
        firstOffset.animateTo(-200f, animationSpec = tween(5000))

        delay(2000)

        // Второй — вниз
        secondOffset.snapTo(0f)
        secondAlpha.animateTo(1f)
        secondOffset.animateTo(200f, animationSpec = tween(5000))
    }

    // Основной контейнер
    Box(modifier = Modifier.fillMaxSize()) {
        if (firstChild != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = firstOffset.value.dp)
                    .alpha(firstAlpha.value)
            ) {
                firstChild()
            }
        }

        if (secondChild != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = secondOffset.value.dp)
                    .alpha(secondAlpha.value)
            ) {
                secondChild()
            }
        }
    }
}