package app.what.features.main.presentation.presentation

import android.graphics.Color
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import app.what.domain.model.Course
import app.what.features.main.R
import app.what.features.main.databinding.CourseCartBinding
import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
fun courseAdapterDelegate(onFavoriteClick: (Course) -> Unit) =
    adapterDelegateViewBinding<Course, Course, CourseCartBinding>(
        { layoutInflater, root -> CourseCartBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.tvTitle.text = item.title
            binding.tvPrice.text = "${item.price} ₽"
            binding.tvRating.text = item.rate.toString()

            binding.tvDate.text = formatCourseDate(item.publishDate)
            binding.tvDescription.text = item.text
            binding.tvDescription.maxLines = 2
            binding.tvDescription.ellipsize = TextUtils.TruncateAt.END

            binding.ivCourseImage.load("https://cdn.idaprikol.ru/images/c27e0d851029bf1a1b8d4ddf923b73fe282b305a11552d70b6b9a820a88233c6_1.jpg") {
                crossfade(true)
                placeholder(app.what.core.R.drawable.ic_vk)
                error(app.what.core.R.drawable.ic_ok)
            }

            val iconRes =
                if (item.hasLike) R.drawable.ic_like_filled else R.drawable.ic_like_outline
            binding.btnFavorite.setImageResource(iconRes)

            binding.btnFavorite.setOnClickListener { onFavoriteClick(item) }
        }
    }

private fun formatCourseDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.getDefault())

    return date.format(formatter)
}