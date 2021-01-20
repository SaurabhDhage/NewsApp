from socket import *
s=socket()
s.bind(('192.168.42.106',8888))
print("File Server is Listening")
s.listen()
path='D:\\abc\\0.mp4'
f=open(path,'rb+')
c,addr=s.accept()
print("Client Connected",str(addr))
for line in f:
    c.send(line)
	
	


	
    


