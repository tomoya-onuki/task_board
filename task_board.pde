PFont myfont;

void setup() {
  size(1200, 800);
  surface.setResizable(true);             // ウィンドウサイズを可変に設定
  surface.setTitle("Task Board");           // ウィンドウタイトル
  myfont = createFont("YuGo-Medium",36);  // フォントの作成
  setup_text_field();                     // テキストフィールドの設定
  setup_button();                         // ボタンの設定
  setup_trashArea();                      // ゴミ箱の設定
  taskSize = 0;
}

void draw() {
  background(255);

  // リストの描画
  lists_draw();

  // ボタンの描画
  button.setPlace(int(width*0.05)+170, height - 80);
  button.draw();

  // ゴミ箱の描画
  trashArea.setPlace(width - int(width*0.05)-80, height - 80);
  trashArea.draw();

  // タスクの描画
  for(int i = 0; i < taskSize; i++) {
    task[i].draw();
    if(task[i].mousePicked && trashArea.over()) taskDelete(i);
  }
  if(taskSize == 0) cursor(ARROW);
  // println(taskSize);

  // テキストエリアの描画
  scrollPane.setBounds(int(width*0.05), height - 80, 150, 50);
  // pane.add(scrollPane);
}

void keyPressed() {
  if (keyCode==ENTER) {
    area.setText("\n");
  }
  if(key == 'q') exit();
}

void mouseClicked(){
  if( button.over() ) {
    generateTask();
  }
}

void mousePressed() {
  // タスクの上でマウスを押したら持ち上げる
  for(int i = 0; i < taskSize; i++) {
    if(task[i].over()) {
      task[i].dx = task[i].x - mouseX;
      task[i].dy = task[i].y - mouseY;
      task[i].mousePicked = true;
    }
  }
}

void mouseReleased() {
  // タスクの上でマウスを放したら，タスクオブジェクトを放す
  for(int i = 0; i < taskSize; i++) {
    if(task[i].over()) task[i].mousePicked = false;
  }
}

void mouseDragged() {
  for(int i = 0; i < taskSize; i++) {
    // タスクの移動
    if(task[i].mousePicked) task[i].mousePickUp();
  }
}
