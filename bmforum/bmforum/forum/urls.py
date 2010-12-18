#!/usr/bin/python
# -*- coding: utf-8 -*-

from django.conf.urls.defaults import *
from django.contrib.auth.views import login
urlpatterns = patterns('bmforum.forum.views',
        (r'^$', 'topic'),
        )
