package com.wbelote.climbtracker


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 */
class ChooseProblemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_choose_problem, container, false)

        val rv: RecyclerView = view.findViewById(R.id.rvProblems)
        rv.adapter = ProblemAdapter()
        rv.layoutManager = LinearLayoutManager(view.context)

        return view
    }


    class ProblemAdapter : RecyclerView.Adapter<ProblemAdapter.ProblemViewHolder>() {

        var problems = listOf(
            ProblemInfo(1, "V2", GymInfo.color("Green"), "SG", 20191203)
            , ProblemInfo(1, "V5", GymInfo.color("Yellow"), "AB", 20191203)
            , ProblemInfo(1, "V3", GymInfo.color("Red"), "RC", 20191203)
            , ProblemInfo(1, "V4-", GymInfo.color("Blue"), "Wei", 20191203)
            , ProblemInfo(1, "V6+", GymInfo.color("Pink"), "JP", 20191203)
        )

        class ProblemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val grade: TextView = view.findViewById(R.id.tvProblemGrade)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_problem, parent, false)
            return ProblemViewHolder(view)

        }

        override fun getItemCount(): Int = problems.size

        override fun onBindViewHolder(holder: ProblemViewHolder, position: Int) {

            holder.grade.text = problems[position].grade
            holder.grade.setBackgroundColor(problems[position].color)
            Log.d("ProblemColor", "Problem color id: ${problems[position].color}")

        }
    }

}
