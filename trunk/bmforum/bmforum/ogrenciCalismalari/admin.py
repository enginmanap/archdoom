#!/usr/bin/python
# -*- coding: utf-8 -*-

from django.contrib import admin
from bmforum.ogrenciCalismalari.models import *

admin.site.register(Professor)
admin.site.register(Project)
admin.site.register(Exam)
admin.site.register(Extra)
admin.site.register(Course)
admin.site.register(LectureNote)
