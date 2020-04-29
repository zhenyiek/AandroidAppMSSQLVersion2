package com.example.crudmssql.adapters;
import android.content.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.crudmssql.R;
import com.example.crudmssql.UpdateDelete;
import com.example.crudmssql.model.Employee;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private Context ctx;
    private List<Employee> list;

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView txtItemName, txtItemPhone;
        Button btnItemView;
        ImageView status;
        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            status = (ImageView) itemView.findViewById(R.id.image);
            txtItemName = (TextView) itemView.findViewById(R.id.txtItemName);
            txtItemPhone = (TextView) itemView.findViewById(R.id.txtItemPhone);
            btnItemView = (Button) itemView.findViewById(R.id.btnItemView);
        }
    }

    public EmployeeAdapter(Context ctx, List<Employee> lista) {
        this.ctx = ctx;
        this.list = lista;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employee, parent, false);

        return new EmployeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        final Employee employee = list.get(position);

        holder.txtItemName.setText(employee.getName());
        holder.txtItemPhone.setText(employee.getPhone());
        if("Pending".equals(employee.getPhone())){
            holder.status.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.yellowcircle));
        }
        else if("Cancel".equals(employee.getPhone())){
            holder.status.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.redcircle));
        }
        else if("Done".equals(employee.getPhone())){
            holder.status.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.greencircle));
        }
        holder.btnItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ctx, UpdateDelete.class);
                intent.putExtra("ID", employee.getId());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}

