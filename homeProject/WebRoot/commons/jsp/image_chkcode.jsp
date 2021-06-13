<%@ page contentType="image/jpeg" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*" %><%!
Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
        }
%><%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
String param = request.getParameter("p");
int width=60, height=20;
BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
Graphics g = image.getGraphics();
Random random = new Random();
g.setColor(getRandColor(250,252));
g.fillRect(0, 0, width, height);
g.setFont(new Font("Times New Roman",Font.PLAIN,20));
g.setColor(getRandColor(150,160));
for (int i=0;i<30;i++)
{
 int x = random.nextInt(width);
 int y = random.nextInt(height);
        int xl = random.nextInt(10);
        int yl = random.nextInt(10);
 g.drawLine(x,y,x+xl,y+yl);
}


String sRand="";
for (int i=0;i<4;i++){
    String rand=String.valueOf(random.nextInt(10));

sRand+=rand;

    g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
    g.drawString(rand,13*i+6,16);
}
session.setAttribute(param,sRand);
g.dispose();
ImageIO.write(image, "JPEG", response.getOutputStream());
out.clear();
out = pageContext.pushBody();
%>
