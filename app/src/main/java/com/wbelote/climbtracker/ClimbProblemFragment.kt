package com.wbelote.climbtracker


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 */
class ClimbProblemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_climb_problem, container, false)

        val problem = GymInfo.currentProblem
        problem?.let {
            view.findViewById<TextView>(R.id.tvCurrentGrade).text = it.grade
            val color = GymInfo.color(it.color)
            view.findViewById<TextView>(R.id.tvCurrentGrade)
                .setBackgroundColor(Color.rgb(color.r, color.g, color.b))
            view.findViewById<TextView>(R.id.tvCurrentSetter).text = it.setter
            view.findViewById<TextView>(R.id.tvCurrentDate).text = it.date.toString()
        }

        view.findViewById<Button>(R.id.btnAttemptSubmit)
            .setOnClickListener {
                val start = view.findViewById<Switch>(R.id.swStarted).isChecked
                val finish = view.findViewById<Switch>(R.id.swFinished).isChecked

                val viewModel =
                    ViewModelProvider(this, ClimbViewModelFactory(activity!!.application))
                        .get(ClimbViewModel::class.java)

                viewModel.addAttempt(AttemptInfo(problem!!.id, start, finish, 0))

                val action = ClimbProblemFragmentDirections
                    .actionClimbProblemFragmentToChooseProblemFragment()
                findNavController().navigate(action)
            }

        return view
    }


}
