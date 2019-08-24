import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import javax.swing.*; 
import java.awt.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class task_organizer_java extends PApplet {

PFont myfont;

public void setup() {
  
  surface.setResizable(true);             // ウィンドウサイズを可変に設定
  surface.setTitle("Task Board");           // ウィンドウタイトル
  myfont = createFont("YuGo-Medium",36);  // フォントの作成
  setup_text_field();                     // テキストフィールドの設定
  setup_button();                         // ボタンの設定
  setup_trashArea();                      // ゴミ箱の設定
  taskSize = 0;
}

public void draw() {
  background(255);

  // リストの描画
  lists_draw();

  // ボタンの描画
  button.setPlace(PApplet.parseInt(width*0.05f)+170, height - 80);
  button.draw();

  // ゴミ箱の描画
  trashArea.setPlace(width - PApplet.parseInt(width*0.05f)-80, height - 80);
  trashArea.draw();

  // タスクの描画
  for(int i = 0; i < taskSize; i++) {
    task[i].draw();
    if(task[i].mousePicked && trashArea.over()) taskDelete(i);
  }
  if(taskSize == 0) cursor(ARROW);
  // println(taskSize);

  // テキストエリアの描画
  scrollPane.setBounds(PApplet.parseInt(width*0.05f), height - 80, 150, 50);
  // pane.add(scrollPane);
}

public void keyPressed() {
  if (keyCode==ENTER) {
    area.setText("\n");
  }
  if(key == 'q') exit();
}

public void mouseClicked(){
  if( button.over() ) {
    generateTask();
  }
}

public void mousePressed() {
  // タスクの上でマウスを押したら持ち上げる
  for(int i = 0; i < taskSize; i++) {
    if(task[i].over()) {
      task[i].dx = task[i].x - mouseX;
      task[i].dy = task[i].y - mouseY;
      task[i].mousePicked = true;
    }
  }
}

public void mouseReleased() {
  // タスクの上でマウスを放したら，タスクオブジェクトを放す
  for(int i = 0; i < taskSize; i++) {
    if(task[i].over()) task[i].mousePicked = false;
  }
}

public void mouseDragged() {
  for(int i = 0; i < taskSize; i++) {
    // タスクの移動
    if(task[i].mousePicked) task[i].mousePickUp();
  }
}
class Button{
  float w, h;
  float x, y;
  int col, text_color, moues_clicked_color;
  String label;
  PFont myfont;
  int fontsize;

  Button(){
    w = 100; h = 30;
    x = 0; y = 0;
  }

  public void setPlace(float _x, float _y) {
    x = _x;
    y = _y;
  }
  public void setSize(float _w, float _h) {
    w = _w;
    h = _h;
  }
  public void setLabel(String str) {
    label = str;
  }
  public void setColor(int _col) {
    col = _col;
  }
  public void setMouseClickedColor(int _col) {
    moues_clicked_color = _col;
  }
  public void setTextColor(int _col) {
    text_color = _col;
  }
  public void fontSize(PFont _myfont, int _size) {
    myfont = _myfont;
    fontsize = _size;
  }

  public void draw() {
    rectMode(CORNER);
    if( !over() ) fill(col); noStroke();
    if( over() ) fill(col); noStroke();
    if( over() && mousePressed ) fill(moues_clicked_color); noStroke();
    rect(x, y, w, h);

    fill(text_color);
    textAlign(LEFT, TOP);
    textFont(myfont, 18);
    text(label, x+12, y+16);
  }

  public boolean over(){
    if(x <= mouseX && mouseX <= x+w &&
       y <= mouseY && mouseY <= y+h ) {
      return true;
    }
    return false;
  }
}
// import controlP5.*;
// ControlP5 Button;
//
// void setup_button() {
//   Button = new ControlP5(this);
//   float margin_bottom = 30;
//   float margin_left = width*0.05;
//   int button_height = 50;
//   int button_width = 100;
//
//   textFont(myfont);
//
//   Button.addButton("add_todo")        //関数呼び出し
//     .setLabel("Add to Todo list")       //テキスト
//     .setPosition(margin_left, height-margin_bottom-button_height)
//     .setSize(button_width, button_height)
//     .setColorActive(#88bde6)          //押したときの色
//     .setColorBackground(#4c4c4c)      //通常時の色
//     .setColorForeground(#4c4c4c)      //マウスを乗せた時の色
//     .setColorCaptionLabel(#ffffff);   //テキストの色
// }
//
//
// // todoリストにタスクを追加する。
// void add_todo() {
//   background(#ff0000);
// }
class Task {
  String text;
  float x, y, dx, dy;
  float w, h;
  int col = 0xfffab158;
  int id;
  boolean mousePicked;

  Task(int i) {
    id = i;
    text = area.getText();
    println(text);
    x = 100;
    y = 100;
  }

  public void setColor(int _col){
    col = _col;
  }

  public void draw() {
    textFont(myfont,16);
    // タスクオブジェクトのサイズを設定
    w = textWidth(text) + 20;
    h = 100;

    fill(col); noStroke();
    rectMode(CORNER);
    rect(x, y, w, h);

    fill(0xffffffff);
    textAlign(LEFT, TOP);
    text(text, x+5, y+10);

    // カーソルの変化
    if(over()) cursor(HAND);
    else cursor(ARROW);
  }


  public boolean over(){
    if(x <= mouseX && mouseX <= x+w &&
       y <= mouseY && mouseY <= y+h ) {
      return true;
    }
    return false;
  }

  public void mousePickUp() {
    x = mouseX+dx;
    y = mouseY+dy;
  }

  public void print() {
    println("id: " +id);
    println("Picked: " +mousePicked);
    println("over: " +over());
  }
}

public void taskDelete(int id) {
  if(taskSize > -1) {
    for(int i = id; i < taskSize; i++) {
      task[i] = task[i+1];
    }
    taskSize--;
  }
}
public void lists_draw() {
  float margin_left = width*0.05f;
  float margin_top = 50;
  float margin_right = width*0.05f;
  float margin_buttom = 100;
  float backlog_width = width*0.1f;
  float list_width = (width - margin_left - margin_right - backlog_width)/3;
  float list_height = height - margin_top - margin_buttom;



  // リストの描画
  pushMatrix();
  translate(margin_left,margin_top);

  // プロジェクトバックログ用のカラム
  textFont(myfont,16);
  fill(0xff000000); stroke(0xff000000);                 // 色指定
  textAlign(CENTER, CENTER);
  text("Project\nBacklog", backlog_width/2, -20); // ラベルの描画
  noFill(); stroke(0xff000000);
  rect(0, 0, backlog_width, list_height);

  // その他のカラム
  translate(backlog_width, 0);
  textFont(myfont,24);
  String[] label = {"TODO", "DOING", "DONE"};       // ラベル
  for(int i = 0; i < 3; i++) {
    fill(0xff000000); stroke(0xff000000);                 // 色指定
    textAlign(CENTER, CENTER);
    text(label[i], i*list_width + list_width/2, -20); // ラベルの描画

    noFill(); stroke(0xff000000);                      // 色指定
    rect(i*list_width, 0, list_width, list_height); // 枠の描画
  }
  popMatrix();
}

Button trashArea;
public void setup_trashArea() {
  float margin_bottom = 30;
  float margin_right = PApplet.parseInt(width*0.05f);
  float area_height = 50;
  float area_width = 80;

  trashArea = new Button();
  trashArea.fontSize(myfont, 18);
  trashArea.setLabel("ゴミ箱");
  trashArea.setSize(area_width, area_height);
  trashArea.setPlace(width - margin_right - area_width, height - margin_bottom - area_height);
  trashArea.setColor(0xff4f4f4f);
  trashArea.setMouseClickedColor(0xff4f4f4f);
  trashArea.setTextColor(0xffffffff);
}


JLayeredPane pane;
JTextArea area;
JScrollPane scrollPane;


// テキストフィールドの設定の関数
public void setup_text_field() {
  // SmoothCanvasの親の親にあたるJLayeredPaneを取得
  Canvas canvas = (Canvas) surface.getNative();
  pane = (JLayeredPane) canvas.getParent().getParent();

  int margin_bottom = 30;
  int margin_left = PApplet.parseInt(width*0.05f);
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
public void setup_button() {
  float margin_bottom = 30;
  float margin_left = PApplet.parseInt(width*0.05f)+170;
  float button_height = 50;
  float button_width = 170;

  button = new Button();
  button.fontSize(myfont, 18);
  button.setLabel("Todoリストへ追加");
  button.setSize(button_width, button_height);
  button.setPlace(margin_left, height - margin_bottom - button_height);
  button.setColor(0xff255daa);
  button.setMouseClickedColor(0xff5da5d8);
  button.setTextColor(0xffffffff);
}


Task[] task = new Task[300];
int taskSize; //タスクの数
// タスクの追加
public void generateTask() {
  if(taskSize != 299){ // タスクの数が上限以下である。
    task[taskSize] = new Task(taskSize);
    taskSize++;       // タスクの数をインクリメント
  }
}
  public void settings() {  size(1200, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "task_organizer_java" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
