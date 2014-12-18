using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Net.Sockets;
using System.IO;
using System.Web.Helpers;
using SlideAssistantDesktop.Entities;
using Newtonsoft.Json;

namespace SlideAssistantDesktop.BL
{
    class ChatHandler
    {
        Object lockOb;
        SlidePackage package;
        private TcpClient clientSocket;

        public ChatHandler() 
        {
            lockOb = new Object();
            package = new SlidePackage();
            package.id = "";
            package.type = "desktop";
            package.isClear = "0";
            package.clear = "0";
            package.connectionsNumber = "";
            package.question = "";
            package.wasSetClear = "";
            clientSocket = new TcpClient();
            clientSocket.Connect("127.0.0.1", 50000);
            ThreadStart threadDelegate = new ThreadStart(listen);
            Thread newThread = new Thread(threadDelegate);
            newThread.Start();   
        }

        public void listen()
        {
            while (clientSocket.Connected)
            {
                try
                {
                    NetworkStream netStream = clientSocket.GetStream();
                    byte[] inStream = new byte[10000];
                    netStream.Read(inStream, 0, (int)clientSocket.ReceiveBufferSize);
                    string returndata = System.Text.Encoding.UTF8.GetString(inStream);
                    SlidePackage pack = (SlidePackage)JsonConvert.DeserializeObject(returndata, typeof(SlidePackage));
                    lock (lockOb)
                    {
                        package.id = pack.id;
                        package.clear = pack.clear;
                        package.connectionsNumber = pack.connectionsNumber;
                    }
                }
                catch (SocketException ex)
                {
                    clientSocket.Close();
                }
                catch (IOException ex) {
                }
            }
        }

        public void write(string name, string slideNumber) 
        {
            NetworkStream netStream = clientSocket.GetStream();
            StreamWriter sw = new StreamWriter(netStream);
            lock (lockOb)
            {
                package.name = name;
                package.slideNumber = slideNumber;
                sw.WriteLine(JsonConvert.SerializeObject(package));
                sw.Flush();
            }
        }

        public void disconnect() 
        {
            clientSocket.Close();
        }
    }
}
