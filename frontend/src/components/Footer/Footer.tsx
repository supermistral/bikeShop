import { Divider, IconButton, Link, Stack, Typography } from "@mui/material";
import { Box } from "@mui/system";
import { Link as RouterLink } from 'react-router-dom';
import YouTubeIcon from '@mui/icons-material/YouTube';
import TelegramIcon from '@mui/icons-material/Telegram';
import { styled } from "@mui/styles";


const SubdivisionTypography = styled(Typography)({
    color: '#444',
    marginBottom: '0.5rem !important'
});


const Footer = () => {
    return (
        <Stack spacing={1} sx={{ pb: 2 }}>
            <Stack direction="row" sx={{ mt: 1, justifyContent: 'center' }}>
                <IconButton>
                    <YouTubeIcon />
                </IconButton>
                <Divider orientation="vertical" variant="middle" flexItem />
                <IconButton>
                    <TelegramIcon />
                </IconButton>
            </Stack>
            <Divider />
            <Stack 
                direction="row" 
                sx={{ 
                    my: 1,
                    justifyContent: 'space-between',
                    '& a': { my: 0.25, color: '#666' }
                }}
            >
                <Box>
                    <Typography variant="h4" component="div">Bike Shop</Typography>
                    <Typography variant="subtitle2">+7 123 456 78 90</Typography>
                    <Box sx={{ px: 1, my: 1, borderLeft: '10px solid #ccc', }}>
                        <Typography variant="body2">г.Москва, пр-т Вернадского, 78</Typography>
                        <Typography variant="body2">ПН - СБ с 10:00 до 20:00</Typography>
                    </Box>
                </Box>
                <Stack>
                    <SubdivisionTypography variant="h5">Поддержка</SubdivisionTypography>
                    <Link component={RouterLink} to="/catalog" underline="hover" variant="body2">Каталог</Link>
                    <Link href="#" underline="hover" variant="body2">Как заказать</Link>
                    <Link href="#" underline="hover" variant="body2">Оплата и доставка</Link>
                    <Link href="#" underline="hover" variant="body2">Возврат и обмен</Link>
                    <Link href="#" underline="hover" variant="body2">Условия гарантии</Link>
                </Stack>
                <Stack>
                    <SubdivisionTypography variant="h5">О нас</SubdivisionTypography>
                    <Link href="#" underline="hover" variant="body2">Магазины</Link>
                    <Link href="#" underline="hover" variant="body2">Контакты</Link>
                    <Link href="#" underline="hover" variant="body2">Связаться с нами</Link>
                    <Link href="#" underline="hover" variant="body2">История компании</Link>
                </Stack>
                <Stack>
                    <Link href="#" underline="hover" variant="subtitle2">Политика конфиденциальности</Link>
                    <Link href="#" underline="hover" variant="subtitle2">Договор оферта</Link>
                </Stack>
            </Stack>
            <Divider />
            <Box sx={{ fontSize: '0.8em' }}>
                <Typography component="div" variant="subtitle2">© 2022 - Bike Shop</Typography>
                <Typography component="div" variant="body2" sx={{ fontSize: '0.85em' }}>
                    Веб-сайт не является основанием 
                    для предъявления претензий и рекламаций, информация 
                    является ознакомительной, технические характеристики 
                    товаров могут отличаться от указанных на сайте. 
                </Typography>
            </Box>
        </Stack>
    )
}

export default Footer;