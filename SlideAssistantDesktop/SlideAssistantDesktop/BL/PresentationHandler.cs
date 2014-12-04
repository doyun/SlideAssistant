using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.InteropServices;
using System.Runtime.InteropServices.ComTypes;
using Microsoft.Office.Interop.PowerPoint;
using System.Windows.Forms;

namespace SlideAssistantDesktop.BL
{
    class PresentationHandler
    {
        delegate void SetTextCallback(string text);
        private IConnectionPoint connectionPoint;
        private int cookie;
        private ApplicationClass pp;
        private ChatHandler ch;

        public PresentationHandler() 
        {
            pp = new ApplicationClass();
            pp.Visible = Microsoft.Office.Core.MsoTriState.msoTrue;
            pp.SlideShowNextSlide += new EApplication_SlideShowNextSlideEventHandler(this.SlideShowNextSlide);
            ch = new ChatHandler();
        }

        public void connect()
        {
            // QI for IConnectionPointContainer.
            IConnectionPointContainer connectionPointContainer = (IConnectionPointContainer)pp;
            // Get the GUID of the EApplication interface.
            Guid guid = typeof(EApplication).GUID;

            // Find the connection point.
            connectionPointContainer.FindConnectionPoint(ref guid, out connectionPoint);
            // Call Advise to sink up the connection.
            connectionPoint.Advise(this, out cookie);            
        }

        public void disconnect()
        {
            connectionPoint.Unadvise(cookie);
            //System.Runtime.InteropServices.Marshal.ReleaseComObject(pp);
            GC.Collect();
        }

        public void disconnectFromServer() 
        {
            ch.disconnect();
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
            ch.write(pp.Caption, Wn.View.CurrentShowPosition.ToString());
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
