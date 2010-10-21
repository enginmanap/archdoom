#!/usr/bin/python
# -*- coding: utf-8 -*-

#
# hello1.py
#
import sys
from PyQt4 import Qt

app = Qt.QApplication(sys.argv)
button=Qt.QPushButton("Hello World", None)
button.show()
app.exec_()
