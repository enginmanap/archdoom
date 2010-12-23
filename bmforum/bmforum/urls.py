from django.conf.urls.defaults import *
# Uncomment the next two lines to enable the admin:
from django.contrib import admin
from os import path
DOCUMENT_ROOT = path.abspath(path.dirname(__file__))
admin.autodiscover()

urlpatterns = patterns('',
    # Example:
    # (r'^bmforum/', include('bmforum.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # (r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    (r'^$', 'bmforum.forum.views.giris'),
    (r'^admin/', include(admin.site.urls)),
    (r'^forum/', include('bmforum.forum.urls')),
    (r'^gezegen/', include('bmforum.planet.urls')),
    (r'^media/(.*)$', 'django.views.static.serve', {'document_root': '%s/media' % DOCUMENT_ROOT, 'show_indexes': True}),
    url(r'^auth/$','forum.views.auth', name='auth'),
    url(r'^deauth/$','forum.views.deauth', name='deauth'),
    url(r'^authlogin/$','forum.views.authlogin', name='authlogin'),
    (r'^member/', include('bmforum.member.urls')),
    (r'^planet/', include('bmforum.planet.urls')),
    (r'^ogrenciCalismalari/', include('bmforum.ogrenciCalismalari.urls')),
)
