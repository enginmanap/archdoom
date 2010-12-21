# Create your views here.
from django.shortcuts import render_to_response, get_object_or_404
from django.http import HttpResponse
from django.core.context_processors import csrf
from bmforum.forum.models import *
from bmforum.forum.forms import EntryForm, TopicForm
from django.template import RequestContext
from django.http import HttpResponseRedirect
from django.core.urlresolvers import reverse
from django.contrib.auth import authenticate, login, logout

def giris(request):
    return render_to_response('base.html', context_instance=RequestContext(request))

def topicList(request, topic_id=0):
    if topic_id == 0:
        topic_list = Topic.objects.filter(subTopic = None, isHidden=False )
        return render_to_response('forum/topiclist.html', {'topic_list':topic_list,"root":True}, context_instance=RequestContext(request))
    else:
        topic = get_object_or_404(Topic, pk = topic_id)
        topic_list = Topic.objects.filter(subTopic = topic, isHidden=False )
        return render_to_response('forum/topiclist.html', {'topic_list':topic_list, "topic_id":topic_id}, context_instance=RequestContext(request))

def addTopic(request, topic_id):
    if request.POST:
        if topic_id == "0":
            topicForm = TopicForm(request.POST, prefix ='topicForm')
            entryForm = EntryForm(request.POST, prefix ='entryForm')
            if topicForm.is_valid():
                topic = topicForm.save(commit = False)
                topic.title =  topicForm.cleaned_data['title']
                topic.priority = get_object_or_404(TopicPriorities, pk=1)
                topic.owner = get_object_or_404(Member, user = request.user)
                topic.save()
        if topic_id != "0":
            topicForm = TopicForm(request.POST, prefix ='topicForm')
            entryForm = EntryForm(request.POST, prefix ='entryForm')
            if topicForm.is_valid() and entryForm.is_valid():
                topic = topicForm.save(commit = False)
                topic.title =  topicForm.cleaned_data['title']
                topic.priority = get_object_or_404(TopicPriorities, pk=1)
                topic.owner = get_object_or_404(Member, user = request.user)
                print "sacmaladin mi?"
                topic.subTopic = get_object_or_404(Topic, pk = topic_id)
                topic.save()

                entry = entryForm.save(commit = False)
                entry.member = get_object_or_404(Member, user = request.user)
                entry.date = datetime.now()
                entry.topic = get_object_or_404(Topic, pk = topic.id)
                entry.text = entryForm.cleaned_data['text']
                entry.save()
                topic.firstEntry = get_object_or_404(Entry, pk = entry.id)
                topic.save()
        return HttpResponseRedirect(reverse('bmforum.forum.views.topicList'))
    else:
        topicForm = TopicForm( prefix ='topicForm')
        entryForm = EntryForm( prefix ='entryForm')
        return render_to_response('forum/addTopic.html', {'topicForm': topicForm, 'entryForm':entryForm,'topic_id':topic_id }, context_instance=RequestContext(request))

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
            return HttpResponseRedirect(reverse('bmforum.forum.views.showTopic', args = (topic_id ,topic_name)),)
    else:
        topic = get_object_or_404(Topic, pk = topic_id)
        entry_list = Entry.objects.filter(topic = topic, isHidden=False)
        form = EntryForm()
        return render_to_response('forum/topicentry.html', {'entry_list': entry_list, 'topic':topic, "form":form, }, context_instance=RequestContext(request))

def auth(request):
    username = request.POST['username']
    password = request.POST['password']
    user = authenticate(username=username, password=password)
    if user is not None:
        if user.is_active:
            login(request, user)
            return HttpResponseRedirect(reverse('bmforum.forum.views.topicList'))
        else:
            error = 'disabled account'
            return render_to_response('error.html', {'error': error})
    else:
        error = 'invalid login'
        return render_to_response('error.html', {'error': error})

def deauth(request):
    logout(request)
    return HttpResponseRedirect(reverse('bmforum.forum.views.topicList'))

def authlogin(request):
    return render_to_response('auth.html', context_instance=RequestContext(request))
