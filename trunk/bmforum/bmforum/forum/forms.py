#!/usr/bin/python
# -*- coding: utf-8 -*-


from django import forms
from bmforum.forum.models import Entry, Topic

class EntryForm(forms.ModelForm):
    text  = forms.CharField(widget=forms.Textarea(attrs={'style':'width: 760px;'}))
    class Meta:
        model = Entry
        fields = ('text',)
        
class TopicForm(forms.ModelForm):
    class Meta:
        model = Topic
        fields = ('title',)