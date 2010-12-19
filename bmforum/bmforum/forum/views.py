# Create your views here.
from django.shortcuts import render_to_response, get_object_or_404
from django.http import HttpResponse
from django.core.context_processors import csrf
from bmforum.forum.models import *
from bmforum.forum.forms import EntryForm
from django.template import RequestContext
from django.http import HttpResponseRedirect
from django.core.urlresolvers import reverse
from django.contrib.auth import authenticate, login, logout

def giris(request):
    return render_to_response('base.html', context_instance=RequestContext(request))

def topicList(request):
    topic_list = Topic.objects.all()
    return render_to_response('forum/topiclist.html', {'topic_list':topic_list})


def showTopic(request, topic_id, topic_name):
    if request.POST:
        form = EntryForm(request.POST)
        if form.is_valid():
            entry = form.save(commit = False)
            entry.member = get_object_or_404(Member, user = request.user)
            entry.date = datetime.now()
            entry.topic = get_object_or_404(Topic, pk = topic_id)
            entry.text = form.cleaned_data['text']
            entry.save()
            return HttpResponseRedirect(reverse('bmforum.forum.views.showTopic', args = (topic_id ,topic_name)))
    else:
        topic = get_object_or_404(Topic, pk = topic_id)
        entry_list = Entry.objects.filter(topic = topic)
        form = EntryForm()
        try:
            user = get_object_or_404(Member, user = request.user)
            print "user :",user.user.username
            return render_to_response('forum/topicentry.html', {'entry_list': entry_list, 'topic':topic, "form":form,"user":user}, context_instance=RequestContext(request))
        except:
            return render_to_response('forum/topicentry.html', {'entry_list': entry_list, 'topic':topic, "form":form, }, context_instance=RequestContext(request))

def auth(request):
    username = request.POST['username']
    password = request.POST['password']
    user = authenticate(username=username, password=password)
    if user is not None:
        if user.is_active:
            login(request, user)
            print "login"
            return HttpResponseRedirect(reverse('bmforum.forum.views.topicList'))
        else:
            # Return a 'disabled account' error message
            return HttpResponseRedirect(reverse('bmforum.forum.views.topicList'))
    else:
        # Return an 'invalid login' error message.
        return HttpResponseRedirect(reverse('bmforum.forum.views.topicList'))
    
def deauth(request):
    logout(request)
    return HttpResponseRedirect(reverse('bmforum.forum.views.topicList'))