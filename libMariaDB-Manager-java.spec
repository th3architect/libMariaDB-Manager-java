%define _topdir	 	%(echo $PWD)/
%define name		libMariaDB-Manager-java
%define release		##RELEASE_TAG##
%define version 	##VERSION_TAG##
%define buildroot 	%{_topdir}/%{name}-%{version}-%{release}-root
%define install_path	/usr/local/skysql/share/

BuildRoot:		%{buildroot}
BuildArch:              noarch
Summary: 		MariaDB Manager Java Library
License: 		GPL
Name: 			%{name}
Version: 		%{version}
Release: 		%{release}
Source: 		%{name}-%{version}-%{release}.tar.gz
Prefix: 		/
Group: 			Development/Tools
Requires:		java-1.7.0-openjdk, rsyslog
#BuildRequires:		java-1.7.0-openjdk

%description
MariaDB Manager is a tool to manage and monitor a set of MariaDB
servers using the Galera multi-master replication form Codership.
This component is the monitor for the MariaDB Manager, it probes
the databases within control of the system gathering performance
and statistics data from the servers.

%prep

%setup -q

%build

%post

%install
mkdir -p $RPM_BUILD_ROOT%{install_path}
cp libMariaDB-Manager-java.jar $RPM_BUILD_ROOT%{install_path}

%clean

%files
%defattr(-,root,root)
%{install_path}
%{install_path}libMariaDB-Manager-java.jar

%changelog

* Thu Nov 07 2013 Massimo Siani <massimo.siani@skysql.com> - 0.1
- Initial commit
