import { SiNotion, SiGithub } from 'react-icons/si';

import logo from '../../assets/logo.svg';
import { FooterSection } from '../../styles/Footer/Footer';

function Footer() {
  return (
    <FooterSection>
      <div>
        <img className="w-[120px] mb-3" src={logo} alt="로고" />
        <div className="text-lg font-semibold">개인정보처리방침</div>
        <div>팀장: 김태우</div>
        <div>부팀장: 강동우</div>
        <div>팀원: 김민지, 현채은, 신이수, 이용만</div>
      </div>
      <div className="flex">
        <a href="https://www.notion.so/codestates/IE5-8c32fc4f250f4bfea452017e93129644?pvs=4">
          <SiNotion className="mr-5" size="40" />
        </a>
        <a href="https://github.com/codestates-seb/seb44_main_005">
          <SiGithub size="40" />
        </a>
      </div>
    </FooterSection>
  );
}

export default Footer;
