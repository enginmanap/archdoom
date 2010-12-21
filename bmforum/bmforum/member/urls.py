from django.conf.urls.defaults import *
# Uncomment the next two lines to enable the admin:
from django.contrib import admin
from os import path
DOCUMENT_ROOT = path.abspath(path.dirname(__file__))
admin.autodiscover()

urlpatterns = patterns('',
    url(r'^userRegister/$','member.views.userRegister', name='userRegister'),
    url(r'^privateMessage/$','member.views.privateMessage', name='privateMessage'),
    url(r'^privateCompose/$','member.views.privateCompose', name='privateCompose'),
    url(r'^privateInbox/$','member.views.privateInbox', name='privateInbox'),
    url(r'^privateSent/$','member.views.privateSent', name='privateSent'),
)
