package com.wbelote.climbtracker


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
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

        // Button to reset area
        view.findViewById<Button>(R.id.btnAreaReset)
            .setOnClickListener {
                val action = ChooseProblemFragmentDirections
                    .actionChooseProblemFragmentToResetAreaFragment()
                view.findNavController().navigate(action)
            }

        val rv: RecyclerView = view.findViewById(R.id.rvProblems)
        val adapter = ProblemAdapter()
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(view.context)

        val viewModel = ViewModelProvider(this, ClimbViewModelFactory(activity!!.application))
            .get(ClimbViewModel::class.java)

        viewModel.areaDates.observe(this, Observer { areaDates ->
            val date = areaDates.firstOrNull { it.area == GymInfo.currentArea.id }?.date ?: -1
            view.findViewById<TextView>(R.id.tvAreaSetDate)
                .text = String.format("Set on %d/%d", (date / 100) % 100, date % 100)
        })

        viewModel.areaProblems.observe(this, Observer { problems ->
            adapter.setProblems(problems)
        })

        return view
    }


    class ProblemAdapter : RecyclerView.Adapter<ProblemAdapter.ProblemViewHolder>() {

        private var problems = listOf<Problem>()

        class ProblemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val grade: TextView = view.findViewById(R.id.tvProblemGrade)
            val setter: TextView = view.findViewById(R.id.tvProblemSetter)
            val date: TextView = view.findViewById(R.id.tvProblemDate)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_problem, parent, false)
            return ProblemViewHolder(view)

        }

        override fun getItemCount(): Int = problems.size

        override fun onBindViewHolder(holder: ProblemViewHolder, position: Int) {

            holder.grade.text = problems[position].grade
            val color = GymInfo.color(problems[position].color)
            val colorValue = Color.rgb(color.r, color.g, color.b)
            holder.grade.setBackgroundColor(colorValue)

            holder.setter.text = problems[position].setter
            holder.date.text = problems[position].date.toString()

            holder.view.setOnClickListener {
                GymInfo.currentProblem = problems[position]
                val action = ChooseProblemFragmentDirections
                    .actionChooseProblemFragmentToClimbProblemFragment()
                holder.view.findNavController().navigate(action)
            }

        }

        internal fun setProblems(p: List<Problem>) {
            problems = p
            notifyDataSetChanged()
        }
    }

}
