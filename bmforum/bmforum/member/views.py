# Create your views here.
from django.http import HttpResponseRedirect
from django.core.urlresolvers import reverse
from django.shortcuts import render_to_response, get_object_or_404
from django.template import RequestContext
from bmforum.member.forms import MemberForm, PrivateMessageForm
from django.contrib.auth.models import User, Group
from bmforum.member.models import Member, PrivateMessage
from django.contrib.auth import authenticate, login, logout
from datetime import datetime

def privateMessage(request, hede=0):
    if request.POST:
        form = PrivateMessageForm(request.POST)
        if form.is_valid():
            message = form.save(commit = False)
            message.pmFrom = get_object_or_404(Member, user=request.user)
            message.date = datetime.now()
            message.title = form.cleaned_data['title']
            pmto = form.cleaned_data['pmTo']
            _user = get_object_or_404(User, username=pmto)
            message.pmTo = get_object_or_404(Member, user=_user)
            message.text = form.cleaned_data['text']
            message.save()

            return render_to_response('member/privateMessage.html', context_instance=RequestContext(request))
    else:
        return render_to_response('member/privateMessage.html', context_instance=RequestContext(request))

def privateCompose(request):
    form = PrivateMessageForm()
    return render_to_response('member/privateCompose.html', {'form':form, }, context_instance=RequestContext(request))

def privateInbox(request):
    message_list = PrivateMessage.objects.filter(pmTo = get_object_or_404(Member, user=request.user))
    return render_to_response('member/privateInbox.html', {'message_list': message_list,}, context_instance=RequestContext(request))

def privateSent(request):
    message_list = PrivateMessage.objects.filter(pmFrom = get_object_or_404(Member, user=request.user))
    return render_to_response('member/privateSent.html', {'message_list': message_list,}, context_instance=RequestContext(request))

def userRegister(request):
    if request.method == 'POST':
        form = MemberForm(request.POST)
        if form.is_valid():
            username = form.cleaned_data['username']
            password = form.cleaned_data['password']
            user = User.objects.create_user(username, form.cleaned_data['email'], password)
            user.first_name = form.cleaned_data['firstname']
            user.last_name = form.cleaned_data['lastname']
            group = get_object_or_404(Group, name='normal')
            user.groups.add(group)
            user.save()

            member = Member(user=user)
            member.birthDate = form.cleaned_data['birthDate']
            member.studentNo = form.cleaned_data['studentNo']
            member.studentClass = form.cleaned_data['studentClass']
            member.save()

            logUser = authenticate(username = username, password = password)
            if logUser.is_active:
                login(request, logUser)

            return HttpResponseRedirect(reverse('bmforum.forum.views.topicList'),)
    else:
        form = MemberForm()
        return render_to_response('member/registerMember.html', {"form":form, }, context_instance=RequestContext(request))
