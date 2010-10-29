#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys
try:
    import pygtk
    pygtk.require("2.0")
except:
    pass
try:
    import gtk
    import gtk.glade
except:
    sys.exit(1)

class ComakTestGTK:
    def __init__(self):
        #Set the Glade file
        self.gladefile = "comak-test-gtk.glade"
        self.wTree = gtk.glade.XML(self.gladefile)
        self.window = self.wTree.get_widget("MainWindow")

if __name__ == "__main__":
    hwg = ComakTestGTK()
    gtk.main()
