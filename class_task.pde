class Task {
  String text;
  float x, y, dx, dy;
  float w, h;
  color col = #fab158;
  int id;
  boolean mousePicked;

  Task(int i) {
    id = i;
    text = area.getText();
    println(text);
    x = 100;
    y = 100;
  }

  void setColor(color _col){
    col = _col;
  }

  void draw() {
    textFont(myfont,16);
    // タスクオブジェクトのサイズを設定
    w = textWidth(text) + 20;
    h = 80;

    fill(col); noStroke();
    rectMode(CORNER);
    rect(x, y, w, h);

    fill(#ffffff);
    textAlign(LEFT, TOP);
    text(text, x+5, y+10);

    // カーソルの変化
    if(over()) cursor(HAND);
    else cursor(ARROW);
  }


  boolean over(){
    if(x <= mouseX && mouseX <= x+w &&
       y <= mouseY && mouseY <= y+h ) {
      return true;
    }
    return false;
  }

  void mousePickUp() {
    x = mouseX+dx;
    y = mouseY+dy;
  }

  void print() {
    println("id: " +id);
    println("Picked: " +mousePicked);
    println("over: " +over());
  }
}

void taskDelete(int id) {
  if(taskSize > -1) {
    for(int i = id; i < taskSize; i++) {
      task[i] = task[i+1];
    }
    taskSize--;
  }
}
