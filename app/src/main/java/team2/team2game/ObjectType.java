package team2.team2game;

public class ObjectType {
        private String Name;
        private double Multiplier;
        private int Health;
        private int Texture;
        private int Height;
        private int Width;

        public ObjectType(String name,double multiplier, int health, int texture, int height,
                          int width){
            this.Name = name;
            this.Multiplier = multiplier;
            this.Health = health;
            this.Texture = texture;
            this.Height = height;
            this.Width = width;
        }

        public int getHealth(){
            return this.Health;
        }

        public String getName(){
            return this.Name;
        }

        public void setMultiplier(double multiplier){
            this.Multiplier = multiplier;
        }

        public double getMultiplier(){
            return this.Multiplier;
        }

        public int Texture(){
            return this.Texture;
        }

        public int[] getSize(){
            return new int[]{getHeight(),getWidth()};
        }

        public int getHeight() {
            return this.Height;
        }

        public int getWidth(){
            return this.Width;
        }

}
