#!/usr/bin/python
# -*- coding: utf-8 -*-

from django.conf.urls.defaults import *
from django.contrib.auth.views import login
urlpatterns = patterns('bmforum.forum.views',
        url(r'^$', 'topicList', name='topicList'),
        url(r'^topicList/$','topicList', name='topicList'),
        url(r'^(?P<topic_id>\d+)-(?P<topic_name>.*)/$','showTopic', name='showTopic'),
        )
