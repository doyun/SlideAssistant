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
using SlideAssistantDesktop.BL;

namespace SlideAssistantDesktop
{
    public partial class MainForm : Form
    {
        PresentationHandler ph;
        public MainForm()
        {
            InitializeComponent();
            ph = new PresentationHandler();
        }

        private void buttonConnect_Click(object sender, EventArgs e)
        {
            ph.connect();
        }
        private void buttonDisconnect_Click(object sender, EventArgs e)
        {
            ph.disconnect();
        }

        private void MainForm_Load(object sender, EventArgs e)
        {

        }

        private void MainForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            ph.disconnectFromServer();
        }
    }
}
