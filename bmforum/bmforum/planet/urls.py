from django.conf.urls.defaults import *
# Uncomment the next two lines to enable the admin:
from django.contrib import admin
from os import path
DOCUMENT_ROOT = path.abspath(path.dirname(__file__))
admin.autodiscover()

urlpatterns = patterns('bmforum.planet.views',
    url(r'^planetRegister/$','planetRegister', name='planetRegister'),
    url(r'^planetUnregister/$','planetUnregister', name='planetUnregister'),
)
