import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_g03.Lesson
import com.example.project_g03.databinding.LessonItemsBinding

class LessonsAdapter(
    private val lessons: MutableList<Lesson>,
    private val onLessonClick: (Lesson) -> Unit
) : RecyclerView.Adapter<LessonsAdapter.LessonViewHolder>() {

    inner class LessonViewHolder(val binding: LessonItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val binding = LessonItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = lessons[position]

        holder.binding.apply {
            // Set lesson details
            tvLessonNumber.text = lesson.number.toString()
            tvLessonName.text = lesson.name
            tvLessonDuration.text = lesson.duration

            if (lesson.isCompleted) {
                tvLessonName.setTextColor(Color.GRAY)
                ivCompletion.visibility = View.VISIBLE
            } else {
                tvLessonName.setTextColor(Color.BLACK)
                ivCompletion.visibility = View.GONE
            }
        }

        holder.itemView.setOnClickListener {
            onLessonClick(lesson)
        }
    }

    override fun getItemCount(): Int = lessons.size

    fun updateLessonCompletion(position: Int) {
        notifyItemChanged(position)
    }


}
