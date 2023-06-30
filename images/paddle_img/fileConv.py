# from PIL import Image
# im = Image.open("Arkanoid_Gray_Player.gif")
# for i in range(10):
#     try:
#         im.seek(i)
#         im.save("power_player_"+str(i)+".jpg")
#     except:
#         1

from pygame import *
from glob import *

##pics = glob("*.png")
##pics = [str(i)+".gif" for i in range(8)]
pics = ["frame_"+str(i)+"_delay-0.25s.gif" for i in range(6)]
imgs = [image.load(p) for p in pics]
c=0
for i in imgs:
    image.save(i, "shooter"+str(c)+".png")
    #image.save(i, pics[c].replace(".png",".jpg"))
    print(pics[c])
    c+=1

