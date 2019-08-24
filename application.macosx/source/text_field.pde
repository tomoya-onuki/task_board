import javax.swing.*;
import java.awt.*;
JLayeredPane pane;
JTextArea area;
JScrollPane scrollPane;


// テキストフィールドの設定の関数
void setup_text_field() {
  // SmoothCanvasの親の親にあたるJLayeredPaneを取得
  Canvas canvas = (Canvas) surface.getNative();
  pane = (JLayeredPane) canvas.getParent().getParent();

  int margin_bottom = 30;
  int margin_left = int(width*0.05);
  int text_field_height = 50;
  int text_field_width = 150;

  area = new JTextArea();
  area.setLineWrap(true);
  area.setWrapStyleWord(true);
  scrollPane = new JScrollPane(area);
  scrollPane.setBounds(margin_left,
                  height - margin_bottom - text_field_height,
                  text_field_width,
                  text_field_height);
  pane.add(scrollPane);
}


Button button;
// ボタンの設定用の関数
void setup_button() {
  float margin_bottom = 30;
  float margin_left = int(width*0.05)+170;
  float button_height = 50;
  float button_width = 170;

  button = new Button();
  button.fontSize(myfont, 18);
  button.setLabel("Todoリストへ追加");
  button.setSize(button_width, button_height);
  button.setPlace(margin_left, height - margin_bottom - button_height);
  button.setColor(#255daa);
  button.setMouseClickedColor(#5da5d8);
  button.setTextColor(#ffffff);
}


Task[] task = new Task[300];
int taskSize; //タスクの数
// タスクの追加
void generateTask() {
  if(taskSize != 299){ // タスクの数が上限以下である。
    task[taskSize] = new Task(taskSize);
    taskSize++;       // タスクの数をインクリメント
  }
}
