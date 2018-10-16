package team2.team2game;

class DroppableAsset {
        private String Name;
        private double Multiplier;
        private int Health;
        private int Texture;
        private int Height;
        private int Width;

        DroppableAsset(String name,double multiplier, int health, int texture, int height,
                          int width){
            this.Name = name;
            this.Multiplier = multiplier;
            this.Health = health;
            this.Texture = texture;
            this.Height = height;
            this.Width = width;
        }

        int getHealth(){
            return this.Health;
        }

        String getName(){
            return this.Name;
        }

        void setMultiplier(double multiplier){
            this.Multiplier = multiplier;
        }

        double getMultiplier(){
            return this.Multiplier;
        }

        int getTexture(){
            return this.Texture;
        }

        int[] getSize(){
            return new int[]{getHeight(),getWidth()};
        }

        int getHeight() {
            return this.Height;
        }

        int getWidth(){
            return this.Width;
        }

}
