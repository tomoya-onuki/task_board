void lists_draw() {
  float margin_left = width*0.05;
  float margin_top = 50;
  float margin_right = width*0.05;
  float margin_buttom = 100;
  float backlog_width = width*0.1;
  float list_width = (width - margin_left - margin_right - backlog_width)/3;
  float list_height = height - margin_top - margin_buttom;



  // リストの描画
  pushMatrix();
  translate(margin_left,margin_top);

  // プロジェクトバックログ用のカラム
  textFont(myfont,16);
  fill(#000000); stroke(#000000);                 // 色指定
  textAlign(CENTER, CENTER);
  text("Project\nBacklog", backlog_width/2, -20); // ラベルの描画
  noFill(); stroke(#000000);
  rect(0, 0, backlog_width, list_height);

  // その他のカラム
  translate(backlog_width, 0);
  textFont(myfont,24);
  String[] label = {"TODO", "DOING", "DONE"};       // ラベル
  for(int i = 0; i < 3; i++) {
    fill(#000000); stroke(#000000);                 // 色指定
    textAlign(CENTER, CENTER);
    text(label[i], i*list_width + list_width/2, -22); // ラベルの描画

    noFill(); stroke(#000000);                      // 色指定
    rect(i*list_width, 0, list_width, list_height); // 枠の描画
  }
  popMatrix();
}

Button trashArea;
void setup_trashArea() {
  float margin_bottom = 30;
  float margin_right = int(width*0.05);
  float area_height = 50;
  float area_width = 80;

  trashArea = new Button();
  trashArea.fontSize(myfont, 18);
  trashArea.setLabel("ゴミ箱");
  trashArea.setSize(area_width, area_height);
  trashArea.setPlace(width - margin_right - area_width, height - margin_bottom - area_height);
  trashArea.setColor(#4f4f4f);
  trashArea.setMouseClickedColor(#4f4f4f);
  trashArea.setTextColor(#ffffff);
}
