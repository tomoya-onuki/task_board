class Button{
  float w, h;
  float x, y;
  color col, text_color, moues_clicked_color;
  String label;
  PFont myfont;
  int fontsize;

  Button(){
    w = 100; h = 30;
    x = 0; y = 0;
  }

  void setPlace(float _x, float _y) {
    x = _x;
    y = _y;
  }
  void setSize(float _w, float _h) {
    w = _w;
    h = _h;
  }
  void setLabel(String str) {
    label = str;
  }
  void setColor(color _col) {
    col = _col;
  }
  void setMouseClickedColor(color _col) {
    moues_clicked_color = _col;
  }
  void setTextColor(color _col) {
    text_color = _col;
  }
  void fontSize(PFont _myfont, int _size) {
    myfont = _myfont;
    fontsize = _size;
  }

  void draw() {
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

  boolean over(){
    if(x <= mouseX && mouseX <= x+w &&
       y <= mouseY && mouseY <= y+h ) {
      return true;
    }
    return false;
  }
}
