#!/usr/bin/python
# -*- coding: utf-8 -*-

from django import forms
from bmforum.member.models import Member, PrivateMessage
from django.contrib.auth.models import User

class MemberForm(forms.ModelForm):
    username = forms.CharField(label='Kullanici Adi', max_length=20)
    firstname = forms.CharField(label='Adi', max_length=30)
    lastname = forms.CharField(label='Soyadi', max_length=30)
    birthDate = forms.DateField(label='Dogum Tarihi', input_formats=('%d/%m/%Y', '%d/%m/%Y'), help_text='23/4/1985 gibi')
    email = forms.EmailField(label='E-Posta')
    password = forms.CharField(label='Parola', max_length=32, widget=forms.PasswordInput,)
    password_again = forms.CharField(label='Parola (tekrar)', max_length=32, widget=forms.PasswordInput, help_text='En az 5 karakter')

    class Meta:
        model = Member
        fields =  ('username', 'firstname', 'lastname', 'birthDate', 'email', 'password', 'password_again', 'studentNo', 'studentClass', )

class PrivateMessageForm(forms.ModelForm):
    pmTo = forms.ModelChoiceField(queryset=Member.objects.all())
    title = forms.CharField(label='Baslik', max_length=30)
    text = forms.CharField(label='Mesaj', widget=forms.Textarea(attrs={'style':'width: 760px;'}))

    class Meta:
        model = PrivateMessage
        fields = ('pmTo', 'title', 'text', )
