/*
 * The MIT License
 *
 * Copyright 2017 tibo.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package info.debatty.java.datasets.KDD;

/**
 *
 * @author tibo
 */
public class Connection {

    public double duration;
    public String protocol_type;
    public String service;
    public String flag;
    public int src_bytes;
    public int dst_bytes;
    public boolean land;
    
    /*
    @attribute 'duration' real
@attribute 'protocol_type' {'tcp','udp', 'icmp'}
@attribute 'service' {'aol', 'auth', 'bgp', 'courier', 'csnet_ns', 'ctf', 'daytime', 'discard', 'domain', 'domain_u', 'echo', 'eco_i', 'ecr_i', 'efs', 'exec', 'finger', 'ftp', 'ftp_data', 'gopher', 'harvest', 'hostnames', 'http', 'http_2784', 'http_443', 'http_8001', 'imap4', 'IRC', 'iso_tsap', 'klogin', 'kshell', 'ldap', 'link', 'login', 'mtp', 'name', 'netbios_dgm', 'netbios_ns', 'netbios_ssn', 'netstat', 'nnsp', 'nntp', 'ntp_u', 'other', 'pm_dump', 'pop_2', 'pop_3', 'printer', 'private', 'red_i', 'remote_job', 'rje', 'shell', 'smtp', 'sql_net', 'ssh', 'sunrpc', 'supdup', 'systat', 'telnet', 'tftp_u', 'tim_i', 'time', 'urh_i', 'urp_i', 'uucp', 'uucp_path', 'vmnet', 'whois', 'X11', 'Z39_50'}
@attribute 'flag' { 'OTH', 'REJ', 'RSTO', 'RSTOS0', 'RSTR', 'S0', 'S1', 'S2', 'S3', 'SF', 'SH' }
@attribute 'src_bytes' real
@attribute 'dst_bytes' real
@attribute 'land' {'0', '1'}
@attribute 'wrong_fragment' real
@attribute 'urgent' real
@attribute 'hot' real
@attribute 'num_failed_logins' real
@attribute 'logged_in' {'0', '1'}
@attribute 'num_compromised' real
@attribute 'root_shell' real
@attribute 'su_attempted' real
@attribute 'num_root' real
@attribute 'num_file_creations' real
@attribute 'num_shells' real
@attribute 'num_access_files' real
@attribute 'num_outbound_cmds' real
@attribute 'is_host_login' {'0', '1'}
@attribute 'is_guest_login' {'0', '1'}
@attribute 'count' real
@attribute 'srv_count' real
@attribute 'serror_rate' real
@attribute 'srv_serror_rate' real
@attribute 'rerror_rate' real
@attribute 'srv_rerror_rate' real
@attribute 'same_srv_rate' real
@attribute 'diff_srv_rate' real
@attribute 'srv_diff_host_rate' real
@attribute 'dst_host_count' real
@attribute 'dst_host_srv_count' real
@attribute 'dst_host_same_srv_rate' real
@attribute 'dst_host_diff_srv_rate' real
@attribute 'dst_host_same_src_port_rate' real
@attribute 'dst_host_srv_diff_host_rate' real
@attribute 'dst_host_serror_rate' real
@attribute 'dst_host_srv_serror_rate' real
@attribute 'dst_host_rerror_rate' real
@attribute 'dst_host_srv_rerror_rate' real



0,tcp,supdup,S0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,205,17,1,1,0,0,0.08,0.06,0,255,17,0.07,0.07,0,0,1,1,0,0,1
0,tcp,http,SF,324,2302,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,22,28,0,0,0,0,1,0,0.14,255,255,1,0,0,0,0,0,0,0,5
0,tcp,uucp_path,S0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,228,13,1,1,0,0,0.06,0.07,0,255,13,0.05,0.07,0,0,1,1,0,0,1
0,tcp,ftp_data,S0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,50,11,1,1,0,0,0.22,0.08,0,255,63,0.25,0.02,0.01,0,1,1,0,0,1
0,tcp,Z39_50,S0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,262,12,1,1,0,0,0.05,0.06,0,255,9,0.04,0.07,0,0,1,1,0,0,1
2,tcp,smtp,SF,1591,372,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,2,0,0,0,0,1,0,1,181,147,0.81,0.02,0.01,0,0,0,0,0,5
9052,udp,other,SF,146,105,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,2,0,0,0,0,0.5,0.5,0,255,2,0.01,0.66,0.99,0,0,0,0,0,5
0,tcp,http,SF,290,3006,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,11,11,0,0,0,0,1,0,0,255,255,1,0,0,0,0,0,0,0,5
0,tcp,csnet_ns,S0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,108,11,1,1,0,0,0.1,0.08,0,255,11,0.04,0.07,0,0,1,1,0,0,1
0,udp,private,SF,28,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,80,80,0,0,0,0,1,0,0,255,80,0.31,0.02,0.31,0,0,0,0,0,1
0,tcp,http,SF,255,861,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,44,44,0.02,0.02,0,0,1,0,0,255,255,1,0,0,0,0,0,0,0,5
0,tcp,ftp_data,SF,334,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,1,0,0,4,38,1,0,1,0.18,0,0,0,0,3
0,tcp,ftp_data,S0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,258,17,1,1,0,0,0.07,0.05,0,255,5,0.02,0.07,0,0,1,1,0,0,1
0,tcp,http,SF,302,498,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,10,10,0,0,0,0,1,0,0,131,255,1,0,0.01,0.04,0,0,0,0,5
0,tcp,private,REJ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,61,4,0,0,1,1,0.07,0.1,0,255,4,0.02,0.08,0,0,0,0,1,1,1
0,udp,private,SF,28,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,1,0,0,255,2,0.01,0.02,0.01,0,0,0,0.77,0,1
0,tcp,http,SF,220,1398,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,26,42,0,0,0,0,1,0,0.05,26,255,1,0,0.04,0.03,0,0,0,0,5
0,udp,domain_u,SF,43,69,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,120,120,0,0,0,0,1,0,0,255,245,0.96,0.01,0.01,0,0,0,0,0,5
0,udp,domain_u,SF,44,133,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,73,75,0,0,0,0,1,0,0.03,122,212,0.88,0.02,0.88,0.01,0,0,0.08,0,5
0,icmp,eco_i,SF,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,15,0,0,0,0,1,0,1,2,46,1,0,1,0.26,0,0,0,0,4
0,tcp,uucp,S0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,135,9,1,1,0,0,0.07,0.06,0,255,11,0.04,0.07,0,0,1,1,0,0,1
0,tcp,finger,S0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24,12,1,1,0,0,0.5,0.08,0,255,59,0.23,0.04,0,0,1,1,0,0,1
0,udp,domain_u,SF,43,43,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,148,228,0,0,0,0,1,0,0.01,255,255,1,0,0.01,0,0,0,0,0,5
0,tcp,ftp_data,SF,641,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,1,0,0,175,48,0.25,0.02,0.25,0.04,0,0,0,0,5
0,tcp,private,REJ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,206,4,0,0,1,1,0.02,0.06,0,255,4,0.02,0.08,0,0,0,0,1,1,1
0,tcp,private,REJ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,175,1,0.1,0,0.89,1,0.01,1,0,255,1,0,0.84,0,0,0.07,0,0.62,1,4
0,tcp,http,SF,310,2794,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,4,4,0,0,0,0,1,0,0,255,255,1,0,0,0,0,0,0,0,5
0,tcp,private,REJ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,63,10,0,0,1,1,0.16,0.08,0,255,10,0.04,0.07,0,0,0,0,1,1,1
0,tcp,smtp,SF,696,333,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,1,0,0,109,133,0.39,0.04,0.01,0.02,0,0,0,0,5
0,tcp,netbios_dgm,RSTR,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,1,1,0,0,236,1,0,0.58,0.58,0,0,0,0.58,1,4
0,tcp,http,S0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,52,19,1,1,0,0,0.37,0.08,0,255,67,0.26,0.02,0,0,1,1,0,0,1
0,tcp,netbios_dgm,REJ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,125,7,0,0,1,1,0.06,0.06,0,255,6,0.02,0.07,0,0,0,0,1,1,1
0,tcp,supdup,S0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,168,21,1,1,0,0,0.12,0.06,0,255,21,0.08,0.06,0,0,1,1,0,0,1
0,tcp,http,SF,221,2878,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,1,0,0,21,58,1,0,0.05,0.03,0,0,0,0,5
0,tcp,private,S0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,145,16,1,1,0,0,0.11,0.06,0,255,31,0.12,0.05,0,0,1,1,0,0,1
0,icmp,urp_i,SF,181,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,2,0,0,0,0,0.67,0.67,0,255,93,0.36,0.01,0.46,0,0,0,0,0,5
0,udp,domain_u,SF,42,42,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,14,0,0,0,0,1,0,0.21,84,83,0.99,0.02,0.01,0,0,0,0,0,5
0,tcp,private,S0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,138,6,1,1,0,0,0.04,0.06,0,255,6,0.02,0.07,0,0,1,1,0,0,1
    */

    public void parse(String line) {
        String[] arr = line.split(",");

    }
}
