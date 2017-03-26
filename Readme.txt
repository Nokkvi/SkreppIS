1. Opnið cmd og skrifið ip config. Finnið local ip töluna. 192.168.???.???

2. Opnið forritið í einhverju django umhverfi(mælum með pycharm). Til að geta keyrt bakendann þarf að keyra pip skipanirnar: 
	pip install djangorestframework, pip install markdown, pip install django-filter, pip install djangorestframework-jwt
	
3. Finnið línuna sem byrjar á ALLOWED_HOSTS í settings.py og bætið þar inn ip-tölunni ykkar.

4. Opnið Skreppís í pycharm og skrifið í run: py manage.py runserver 192.168.???.???:8000

5. Nú er hægt að opna skreppIsUI folderinn í android studio. Þar þarf að breyta ip addressum í LoginActivity, RegiserActivity og SearchDriverCriteriaActivity hvar sem stendur baseUrl("ip tala").
