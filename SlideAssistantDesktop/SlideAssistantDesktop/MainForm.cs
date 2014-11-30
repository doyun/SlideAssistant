using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using Microsoft.Office.Interop.PowerPoint;
using System.Net.Sockets;

namespace SlideAssistantDesktop
{
    public partial class MainForm : Form
    {
        delegate void SetTextCallback(string text);
        private UCOMIConnectionPoint connectionPoint;
        private int cookie;
        private ApplicationClass pp;
        TcpClient clientSocket = new TcpClient();
        public MainForm()
        {
            InitializeComponent();
            pp = new ApplicationClass();
            
            // Show PowerPoint to the user.
            pp.Visible = Microsoft.Office.Core.MsoTriState.msoTrue;
            pp.SlideShowNextSlide += new EApplication_SlideShowNextSlideEventHandler(this.SlideShowNextSlide);

            clientSocket.Connect("127.0.0.1", 50000);
            this.listBox1.Items.Add("Client Socket Program - Server Connected ...");
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
                    NetworkStream serverStream = clientSocket.GetStream();
                    

                    byte[] inStream = new byte[10025];
                    serverStream.Read(inStream, 0, (int)clientSocket.ReceiveBufferSize);
                    string returndata = System.Text.Encoding.UTF8.GetString(inStream);
                    
                    this.listBox1.Items.Add(returndata);
                }
                catch (SocketException)
                {
                    clientSocket.Close();
                }
            }
        }

        private void buttonConnect_Click(object sender, EventArgs e)
        {
            // QI for IConnectionPointContainer.
            UCOMIConnectionPointContainer connectionPointContainer = (UCOMIConnectionPointContainer)pp;
            // Get the GUID of the EApplication interface.
            Guid guid = typeof(EApplication).GUID;

            // Find the connection point.
            connectionPointContainer.FindConnectionPoint(ref guid, out connectionPoint);
            // Call Advise to sink up the connection.
            connectionPoint.Advise(this, out cookie);
            //this.listBox1.Items.Add("b2c");
        }
        private void buttonDisconnect_Click(object sender, EventArgs e)
        {
            connectionPoint.Unadvise(cookie);
            //System.Runtime.InteropServices.Marshal.ReleaseComObject(pp);
            GC.Collect();
        }

        private void MainForm_Load(object sender, EventArgs e)
        {
            
        }

        [DispId(2001)]
        public void WindowSelectionChange(Selection Sel)
        {
            //this.listBox1.Items.Add("WindowSelectionChange");
        }

        [DispId(2002)]
        public void WindowBeforeRightClick(Selection Sel, bool Cancel)
        {
            //this.listBox1.Items.Add("WindowBeforeRightClick");
        }

        [DispId(2003)]
        public void WindowBeforeDoubleClick(Selection Sel, bool Cancel)
        {
            //this.listBox1.Items.Add("WindowBeforeDoubleClick");
        }

        [DispId(2004)]
        public void PresentationClose(Presentation Pres)
        {
            //this.listBox1.Items.Add("PresentationClose");
        }

        [DispId(2005)]
        public void PresentationSave(Presentation Pres)
        {
            //this.listBox1.Items.Add("PresentationSave");
        }

        [DispId(2006)]
        public void PresentationOpen(Presentation Pres)
        {
            //this.listBox1.Items.Add("PresentationOpen");
        }

        [DispId(2007)]
        public void NewPresentation(Presentation Pres)
        {
            //this.listBox1.Items.Add("NewPresentation");
        }

        [DispId(2008)]
        public void PresentationNewSlide(Slide Sld)
        {
            //this.listBox1.Items.Add("PresentationNewSlide");
        }

        [DispId(2009)]
        public void WindowActivate(Presentation Pres, DocumentWindow Wn)
        {
            //this.listBox1.Items.Add("WindowActivate");
        }

        [DispId(2010)]
        public void WindowDeactivate(Presentation Pres, DocumentWindow Wn)
        {
            //this.listBox1.Items.Add("WindowDeactivate");
        }

        [DispId(2011)]
        public void SlideShowBegin(SlideShowWindow Wn)
        {
            //this.listBox1.Items.Add("SlideShowBegin");
        }

        [DispId(2012)]
        public void SlideShowNextBuild(SlideShowWindow Wn)
        {
            //this.listBox1.Items.Add("SlideShowNextBuild");
        }

        [DispId(2013)]
        public void SlideShowNextSlide(SlideShowWindow Wn)
        {
            SetText("SlideShowNextSlide" + Wn.View.CurrentShowPosition + Wn.View.Slide.Background);
        }

        public void SetText(string text)
        {
            if (this.listBox1.InvokeRequired)
            {
                SetTextCallback d = new SetTextCallback(SetText);
                this.Invoke(d, new object[] { text });
            }
            else
            {
                this.listBox1.Items.Add(text);
            }
        }

        [DispId(2014)]
        public void SlideShowEnd(Presentation Pres)
        {
            //this.listBox1.Items.Add("SlideShowEnd");
        }

        [DispId(2015)]
        public void PresentationPrint(Presentation Pres)
        {
            //this.listBox1.Items.Add("PresentationPrint");
        }

        [DispId(2016)]
        public void SlideSelectionChanged(SlideRange SldRange)
        {
            //this.listBox1.Items.Add("SlideSelectionChanged");
        }

        [DispId(2017)]
        public void ColorSchemeChanged(SlideRange SldRange)
        {
            //this.listBox1.Items.Add("ColorSchemeChanged");
        }

        [DispId(2018)]
        public void PresentationBeforeSave(Presentation Pres, bool Cancel)
        {
            //this.listBox1.Items.Add("PresentationBeforeSave");
        }

        [DispId(2019)]
        public void SlideShowNextClick(SlideShowWindow Wn, Effect nEffect)
        {
            //this.listBox1.Items.Add("SlideShowNextClick");
        }

    }
}
