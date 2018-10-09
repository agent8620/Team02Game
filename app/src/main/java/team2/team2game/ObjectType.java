package team2.team2game;

public class ObjectType {
        private String Name = "";
        private double Multiplier = 0;
        private int Health = 0;
        private int Texture = 0;
        private int Height = 0;
        private int Width = 0;

        public ObjectType(String name,double multiplier, int health, int texture, int height,
                          int width){
            Name = name;
            Multiplier = multiplier;
            Health = health;
            Texture = texture;
            Height = height;
            Width = width;
        }
}
