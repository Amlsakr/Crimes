package aml.crimes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class CrimeListFragment extends Fragment {

RecyclerView mCrimeRecyclerView ;
    CrimeAdapter mAdapter ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return  view ;
    }

    private void  updateUI () {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else  {
            mAdapter.notifyDataSetChanged();
        }
    }


    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private Crime mCrime ;
        private TextView mTitleTextView ;
        private TextView mDateTextView ;
        private ImageView mSolvedImageView ;

        public CrimeHolder(LayoutInflater layoutInflater ,  ViewGroup parent) {
            super(layoutInflater.inflate(R.layout.list_item_crime  , parent , false));

            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.crime_tv_number);
            mDateTextView = itemView.findViewById(R.id.crime_tv_date);
            mSolvedImageView = itemView.findViewById(R.id.crime_IV_solved);
        }

        public void bind (Crime crime ) {
            mCrime = crime ;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {

            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);

        }
    }


    private class CrimeAdapter  extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes ;

        public CrimeAdapter(List<Crime> mCrimes) {
            this.mCrimes = mCrimes;
        }


        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return  new CrimeHolder(layoutInflater , parent);

        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {

            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }



}
