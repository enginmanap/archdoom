# Create your views here.
from django.shortcuts import render_to_response, get_object_or_404
from django.template import RequestContext
from bmforum.planet.forms import BlogForm
from bmforum.member.models import Member
from bmforum.planet.models import Blog
from django.db import connection
from django.http import HttpResponseRedirect
from django.core.urlresolvers import reverse

def blogList(request):
    blogsList = Blog.objects.all()
    print connection.queries
    return render_to_response('planet/blogList.html', {'blogList':blogsList}, context_instance=RequestContext(request))

def planetRegister(request):
    if request.POST:
        form = BlogForm(request.POST)
        if form.is_valid():
            blog = form.save(commit = False)
            _user = request.user
            blog.member = get_object_or_404(Member, user = _user)
            blog.blogAddress = form.cleaned_data['blogAddress']
            blog.save()
            print connection.queries
        #    return render_to_response('member/userMenu.html', context_instance=RequestContext(request))
            return HttpResponseRedirect(reverse('userMenu'), )
        else:
            error = "form is not valid"
            return render_to_response('error.html', { 'error': error }, context_instance=RequestContext(request))
    else:
        form = BlogForm()
        print connection.queries
        return render_to_response('planet/registerPlanet.html', {'form':form, }, context_instance=RequestContext(request))


def planetUnregister(request):
    print "unregister"
    _member = get_object_or_404(Member, user = request.user)
    blog = get_object_or_404(Blog, member = _member)
    '''
    blog.isHidden = True
    blog.save()
    '''
    blog.delete()
    print connection.queries
#    return render_to_response('member/userMenu.html', context_instance=RequestContext(request))
    return HttpResponseRedirect(reverse('userMenu'), )
