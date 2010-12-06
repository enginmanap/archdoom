#!/usr/bin/python
# -*- coding: utf-8 -*-

from django.contrib import admin
from bmforum.forum.models import *

admin.site.register(Entry)
admin.site.register(TopicPriorities)
admin.site.register(Topic)
admin.site.register(PrivateMessage)
