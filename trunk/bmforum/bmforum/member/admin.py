#!/usr/bin/python
# -*- coding: utf-8 -*-

from django.contrib import admin
from bmforum.member.models import *

admin.site.register(Member)
admin.site.register(NotifyOptions)
admin.site.register(PrivateMessage)
