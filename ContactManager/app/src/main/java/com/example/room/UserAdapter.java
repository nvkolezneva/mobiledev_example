package com.example.room;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
//Класс, который связывает данные, которые мы хотим добавить в список, и RecyclerView
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    List<User> list;
    private View selected;

    UserAdapter(Context context, List<User> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    //onCreateViewHolder() создает новый объект ViewHolder всякий раз, когда RecyclerView нуждается в этом.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }
//onBindViewHolder принимает объект ViewHolder и устанавливает необходимые данные для соответствующей строки во view-компоненте
//При onClick открываем edit активити, ожидая результат
//При этом intent запускает "активности" у этого механизма
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        User user = list.get(position);
        holder.textID.setText(String.valueOf(user.id));
        holder.textName.setText(user.name);
        holder.textEmail.setText(user.email);
        holder.line.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (selected != null){
                    selected.setBackgroundColor(0);
                }
                v.setBackgroundColor(context.getColor(R.color.colorAccent));
                selected = v;
                User user = list.get(position);
                ((MainActivity) context).position = position;
                ((MainActivity) context).editName.setText(user.name);
                ((MainActivity) context).editEmail.setText(user.email);
            }
        });
    }
//Возвращает количество элементов списка
    @Override
    public int getItemCount() {
        return list.size();
    }
    //Класс ViewHolder сохраняет ссылки на необходимые в элементе списка шаблоны.
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textID, textName, textEmail;
        LinearLayout line;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textID = itemView.findViewById(R.id.textID);
            textName = itemView.findViewById(R.id.textName);
            textEmail = itemView.findViewById(R.id.textEmail);
            line = itemView.findViewById(R.id.line);
        }
    }
}